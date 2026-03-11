# Chat App

A full-stack chat application that combines traditional messaging with AI-powered responses. Users create an account, start chat threads, send messages, and receive instant AI-generated replies using Groq’s LLaMA 3.1 model—similar to a lightweight ChatGPT-style experience.

---

## Demo
<img width="1895" height="844" alt="image" src="https://github.com/user-attachments/assets/2ca901ac-a228-4470-be58-1a356a24f2b6" />

![Chat App Screenshot](assets/chat-app-demo.png)

*Chat interface with sidebar, message threads, and AI-powered replies.*

---

## What is it?

**Chat App** is a full-stack web application with:

- A **Next.js** frontend for the chat UI and user flows
- A **Spring Boot** backend that handles auth, persistence, and AI integration
- **PostgreSQL** for users, chats, and messages
- **Groq API** for fast AI completions (LLaMA 3.1)

Users log in or sign up, create named chat threads, type messages, and the backend sends each message to Groq and returns the AI response. Everything is stored in the database so conversations can be revisited.

---

## How it works

1. **Authentication** — Users sign up or log in. The backend issues a JWT, which the frontend stores and sends with each request.
2. **Chat threads** — Each user can create multiple chats (e.g. “General”, “Product Roadmap”). The sidebar lists them; clicking switches the active conversation.
3. **Messaging** — When a message is sent:
   - The frontend POSTs it to the backend
   - The backend saves the user message in PostgreSQL
   - The backend calls Groq’s API with the message content
   - Groq returns a completion; the backend saves it and returns it to the frontend
4. **Profile** — Users can view and edit their profile (name, username, email, password).

The frontend runs on port 3000; the backend on 8080. The frontend calls the backend REST API for all data and AI responses.

---

## Features

| Feature | Description |
| -------- | ----------- |
| **Sign up & login** | Basic auth with JWT. Users create accounts and log in; JWT is used for subsequent API calls. |
| **Chat threads** | Create multiple chats, switch between them in the sidebar, and view conversation history. |
| **AI responses** | Each user message is sent to Groq (LLaMA 3.1). The AI reply is streamed back and shown in the thread. |
| **Persistent history** | All messages are stored in PostgreSQL and can be loaded when reopening a chat. |
| **Profile management** | View and update name, username, email, and password. |
| **Responsive UI** | Layout adapts to different screen sizes. |

---

## Tech stack

| Layer | Technology | Role |
| ----- | ---------- | ---- |
| **Frontend** | Next.js 15, React 19 | Pages, routing, UI |
| **HTTP** | Axios | API calls to backend |
| **Backend** | Spring Boot, Java 17 | REST API, business logic |
| **Security** | Spring Security, JWT | Auth and protected routes |
| **Database** | PostgreSQL | Users, chats, messages |
| **AI** | Groq API (LLaMA 3.1) | Chat completions |
| **Auth** | Basic + JWT | Login and session handling |

---

## Project structure

```
ChatApp/
├── frontend/                    # Next.js app
│   ├── src/
│   │   ├── components/          # Header, Footer
│   │   ├── pages/               # index (chat), login, profile
│   │   ├── services/            # userService (auth helpers)
│   │   └── styles/              # CSS modules, global styles
│   └── package.json
├── spring-api/                  # Spring Boot API
│   ├── src/main/java/
│   │   └── app/chat/springapi/
│   │       ├── controllers/     # Users, Chats, Messages
│   │       ├── services/        # MessageService, CompletionApiService, etc.
│   │       ├── models/          # User, Chats, Message
│   │       └── SecurityConfiguration
│   └── src/main/resources/
│       └── application.properties
└── README.md
```

---

## Prerequisites

- **Node.js** 18+ (for frontend)
- **Java 17** (for backend)
- **Maven** (to build/run Spring Boot)
- **PostgreSQL** (for persistence)
- **Groq API key** — [Get one here](https://console.groq.com/keys)

---

## Getting started

### 1. Database

Create a PostgreSQL database (e.g. `postgres` on `localhost:5434`) and update `spring-api/src/main/resources/application.properties` to match your setup. Use `application.properties.example` as a template if needed.

### 2. Groq API key

Set the `LLMS_GROQ_KEY` environment variable before starting the backend:

```bash
# Windows (PowerShell)
$env:LLMS_GROQ_KEY="gsk_your_key_here"

# macOS / Linux
export LLMS_GROQ_KEY="gsk_your_key_here"
```

### 3. Backend

```bash
cd spring-api
mvn spring-boot:run
```

The API will be available at [http://localhost:8080](http://localhost:8080).

### 4. Frontend

```bash
cd frontend
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

---

## Configuration

| Setting | File | Description |
| ------- | ---- | ----------- |
| DB URL, user, password | `spring-api/.../application.properties` | PostgreSQL connection |
| Groq API key | `LLMS_GROQ_KEY` env var | Used by the backend for AI calls |
| Backend URL | `frontend/src/services/userService.js` | Default: `http://localhost:8080` |

For production, configure DB credentials and Groq key via environment variables or external config; avoid committing real secrets.

---

## API overview

| Endpoint | Method | Description |
| -------- | ------ | ----------- |
| `/users` | POST | Sign up |
| `/login` | GET | Login (Basic auth) → JWT |
| `/users/{username}` | GET, PUT | Get or update user |
| `/chats` | GET, POST | List chats, create chat |
| `/messages/{chatId}` | GET | Get messages for a chat |
| `/messages` | POST | Create message (and trigger AI reply) |

All protected endpoints expect `Authorization: Bearer <JWT>`.

---

## License

MIT
