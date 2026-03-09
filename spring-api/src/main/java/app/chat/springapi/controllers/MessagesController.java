package app.chat.springapi.controllers;

import app.chat.springapi.exceptions.BootcampException;
import app.chat.springapi.models.Chats;
import app.chat.springapi.models.Message;
import app.chat.springapi.models.User;
import app.chat.springapi.models.criteria.ChatCriteria;
import app.chat.springapi.services.ChatService;
import app.chat.springapi.services.MessageService;
import app.chat.springapi.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
public class MessagesController {
    private final UserService userService;
    private final ChatService chatService;
    MessageService messageService;
    public MessagesController(MessageService messageService, UserService userService, ChatService chatService) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping("/messages/{chatId}")
    public List<Message> getMessagesByChat(@PathVariable Long chatId ) throws BootcampException {

        return messageService.getMessagesByChat(chatId);
    }

    // pairnw parametro message kai epistrefw parametro message
    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message, Authentication authentication) {

        Chats loggeningChat=chatService.getChatByUsername(authentication.getName());


        message.setChatId(loggeningChat.getId());
        message.setCreatedAt(Instant.now());
//        message.setCreatedByUserId(loggeningChat.getUser_id());
        message.setCreatedByUserId(String.valueOf(loggeningChat.getUser_id()));

        return messageService.createMessage(message);
    }


    @PostMapping("/messagess")
    public Message createMessages(@RequestBody Message message, Authentication authentication) {
        //keep the json of the user that is logged in
        User loggeningUser=userService.getUserByUsername(authentication.getName());

        //with the id of the loggedin user, we get the chat
        Chats loggeningChat=chatService.getChatById(loggeningUser.getId());
        message.setChatId(loggeningChat.getId());
        message.setCreatedAt(Instant.now());
        //message.setCreatedByUserId(loggeningChat.getUser_id());
        message.setCreatedByUserId(String.valueOf(loggeningChat.getUser_id()));
        return messageService.createMessage(message);
    }
}
