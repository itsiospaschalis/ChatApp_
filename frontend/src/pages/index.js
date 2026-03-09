import Head from "next/head";
import Image from "next/image";
import { Geist, Geist_Mono } from "next/font/google";
import styles from "@/styles/Home.module.css";

import {useCallback, useEffect, useRef, useState} from "react";
import axios from "axios";


const seedThreads = [
    { id: 1,   title: "General",         unread: 0 },
    { id: 2,   title: "Product Roadmap", unread: 2 },
    { id: 3,   title: "Customer Support", unread: 0 }
];

const seedMessages = {
    1: [
        { id: "m1", fromSelf: false, content: "Good morning team! ☕",  createdAt: Date.now() - 60_000 },
        { id: "m2", fromSelf: true,  content: "Morning! Ready for stand‑up.", createdAt: Date.now() - 30_000 },
    ],
    2: [
        { id: "m3", fromSelf: false, content: "Have we locked Q4 features?", createdAt: Date.now() - 86_400_000 },
    ],
    3: [],
};



export default function Home() {


    /* refs */
    const messagesRef = useRef(null);

    /* state */
    const [threads, setThreads]           = useState([]);
    const [activeThreadId, setActiveThreadId] = useState(null);
    const [messages, setMessages]         = useState([]);
    const [draft, setDraft]               = useState("");
    const [user, setUser] = useState({ id :"..." ,username: "...", email: "...", role: "..." },);
    const [newChatTitle, setNewChatTitle] = useState(""); // 👈 state for new chat title


    useEffect(() => {
        setThreads(seedThreads);
        if (seedThreads.length > 0) {
            setActiveThreadId(seedThreads[0].id);
        }
    }, []);

    useEffect(() => {
        if (activeThreadId == null) {
            setMessages([]);
            return;
        }

        const seeded = (seedMessages[activeThreadId] ?? []).map(msg => ({
            ...msg,
            fromSelf: Boolean(msg.fromSelf),
        }));
        setMessages(seeded);
    }, [activeThreadId]);





    /* derived helpers */
    const activeThread = threads.find(t => t.id === activeThreadId);

    /* ---- 3. switching threads ----------------------------------------- */
    const setActiveThread = (id) => {
        setActiveThreadId(id);
    };

    /* ---- 4. sending a message ----------------------------------------- */
    const handleSend = async (e) => {
        e.preventDefault();
        if (!draft.trim()) return;
        if (activeThreadId == null) return;

        const newMsg = {
            content: draft,
            chatId: activeThreadId,
            fromSelf: true,
        };
        setMessages(prev => [...(prev ?? []), { ...newMsg, id: `local-${Date.now()}` }]);
        setDraft("");

        let token = localStorage.getItem("token");

        let resp = await axios.post("http://localhost:8080/messagess", {
            content: draft,
            chatId: activeThreadId,
        }, {
            headers: {"Authorization": `Bearer ${token}`},
        })

        let respMsg = resp.data;
        setMessages(prev => [...prev, { ...respMsg, fromSelf: false }]);

    };

    /*-------------5. Add new chat --------------------------------*/
    const handleNewChat= async (e) => {
        e.preventDefault();

        // if (!draft.trim()) return;
        const newChat={
            title:newChatTitle,
            userId:user.id
        }


        let token=localStorage.getItem("token");
        let resp=await axios.post(`http://localhost:8080/chats`,newChat,
            {headers:{"Authorization":`Bearer ${token}`},})
        let respChat=resp.data;
        setThreads(prev=>[...prev,respChat]);
        setActiveThreadId(respChat.id);
        setMessages([]);
        // clear the input
        setNewChatTitle("");
    }


    return (
        <div className="home chat-page">
            {/* -- Sidebar ---------------------------------------------------------- */}
            <aside className="chat-sidebar">
                <header className="chat-sidebar-header">Chats</header>

                {/*<form onSubmit={handleNewChat} className="new-chat-form">*/}
                {/*    <input*/}
                {/*        type="text"*/}
                {/*        placeholder="Chat title..."*/}
                {/*        value={newChatTitle}*/}
                {/*        onChange={e => setNewChatTitle(e.target.value)}*/}
                {/*        className="new-chat-input"*/}
                {/*    />*/}
                <form onSubmit={handleNewChat} className="new-chat-form">
                    <input
                        type="text"
                        placeholder="Type a chat title !"
                        value={newChatTitle}
                        onChange={e => setNewChatTitle(e.target.value)}
                        className="new-chat-input"
                    />
                    {/*<button type="submit" className="new-chat-btn">*/}
                    {/*    + New Chat*/}
                    {/*</button>*/}
                </form>
                {/*<button className="new-chat-btn" onClick={handleNewChat}>*/}
                {/*    + New Chat*/}
                {/*</button>*/}

                <ul className="chat-thread-list">
                    {threads.map(t => (
                        <li
                            key={t.id}
                            className={`chat-thread-item ${
                                t.id === (activeThread?.id) ? "active" : ""
                            }`}
                            onClick={() => setActiveThread(t.id)}
                        >
                            <span className="thread-title">{t.title}</span>
                            {t.unread > 0 && (
                                <span className="thread-unread">{t.unread}</span>
                            )}
                        </li>
                    ))}
                </ul>
            </aside>

            {/* -- Chat pane -------------------------------------------------------- */}
            <section className="chat-container">
                <header className="chat-header">{activeThread?.title}</header>

                <div className="chat-messages" ref={messagesRef}>
                    {messages && messages.map(m => (
                        <div
                            key={m.id}
                            className={`chat-bubble ${m.fromSelf ? "self" : "other"}`}
                        >
                            <p>{m.content}</p>
                        </div>
                    ))}
                </div>

                <form className="chat-input-bar" onSubmit={handleSend}>
                    <input
                        className="chat-input"
                        type="text"
                        placeholder="Type a message…"
                        value={draft}
                        onChange={e => setDraft(e.target.value)}
                    />
                    <button type="submit" className="chat-send-btn">
                        Send
                    </button>
                </form>
            </section>
        </div>
    );
}