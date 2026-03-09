package app.chat.springapi.services;

import app.chat.springapi.exceptions.BootcampException;
import app.chat.springapi.models.Chats;
import app.chat.springapi.models.criteria.ChatCriteria;
import app.chat.springapi.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

//-------inject REPOSITORY-------------
    private final ChatRepository chatRepository;
    @Autowired
    public ChatService(ChatRepository chatRepository)
    {
        this.chatRepository = chatRepository;
    }

//-------Method to get all chats based on criteria-------------
    public List<Chats> getAll(ChatCriteria criteria) throws BootcampException {
        Instant twoYearAgo= Instant.now().minusSeconds(60*60*24*365*2);
        Date t=new Date();

        if (criteria.getFrom()!=null && criteria.getFrom().isBefore(twoYearAgo)){
            throw new BootcampException(HttpStatus.BAD_REQUEST,
                    "From date cannot be older than 2 years");
        }

        //To repository exei methodo findAll me orisma ena instance ths klasis ChatCriteria
        //to opoio exei enan getter poy exei graftei sto ChatCriteria apo to lombok
        return chatRepository.findAll(criteria.getUserId(),
                criteria.getUsername(),
                criteria.getFrom(),
                criteria.getTo());

    }
    public Chats createChat(Chats chat_) throws BootcampException {
        if (chat_.getTitle()==null || chat_.getTitle().isEmpty()){
            throw new BootcampException(HttpStatus.BAD_REQUEST,
                    "Chat name cannot be empty");
        }


//        chat_.setUser_id(chat_.getUser_id());
//        chat_.setCreated_at(Instant.now());
        return chatRepository.save(chat_);
    }

    public Chats getChatByUsername(@PathVariable String username) {

        return chatRepository.findChatByUsername(username);

    }
    public Chats getChatById(@PathVariable Long Id) {

        return chatRepository.findChatByChatId(Id);

    }

}