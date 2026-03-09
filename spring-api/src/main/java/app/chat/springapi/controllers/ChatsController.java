package app.chat.springapi.controllers;


import app.chat.springapi.exceptions.BootcampException;
import app.chat.springapi.models.Chats;
import app.chat.springapi.models.User;
import app.chat.springapi.models.criteria.ChatCriteria;
import app.chat.springapi.services.ChatService;
import app.chat.springapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatsController {


    //Inject ChatService
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatsController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }


    @GetMapping("/chats")
    public List<Chats> getAllChats(ChatCriteria criteria) throws BootcampException {

        return chatService.getAll(criteria);
    }

//    @GetMapping("/chats/{user_id}")
//    public List<Chats> getChatsByUserId(@PathVariable Long user_id) throws BootcampException {
//
//
//        return chatService.getChatByUserId(user_id);
//    }


    @PostMapping("/chats")
    public Chats createChat(@RequestBody Chats chat_, Authentication authentication) throws BootcampException {
        User loggeningUser=userService.getUserByUsername(authentication.getName());

        chat_.setUser_id(loggeningUser.getId());
        chat_.setUsername(loggeningUser.getUsername());
        chat_.setCreated_at(Instant.now());
        return chatService.createChat(chat_);
    }
}
