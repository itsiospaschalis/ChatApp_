package app.chat.springapi.services;

import app.chat.springapi.models.Message;
import app.chat.springapi.models.User;
import app.chat.springapi.models.dtos.completions.ChatCompletionResponse;
import app.chat.springapi.models.dtos.completions.ResponseMessage;
import app.chat.springapi.repositories.MessageRepository;
import app.chat.springapi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {

    //inject CompletionApiService+MessageRepository to MessageService

    private final MessageRepository messageRepository;
    private CompletionApiService completionApiService;
    public MessageService(CompletionApiService completionApiService, MessageRepository messageRepository) {
        this.completionApiService = completionApiService;
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesByChat(@PathVariable Long chatId)
    {
        return messageRepository.findMessagesByChat(chatId);
    }


    public Message createMessage(Message message) {
//message is an instance of Message that has been passed from the controller and has some fields that were set in the model
        // Save message (not the answer) to the database
        message=messageRepository.save(message);


        //get response from LLM
        String llmResponse=completionApiService.getCompletion(message.getContent());

        //create message instance to use the getter and setters ,
        // to set the content to the response from LLM
        Message llmMessage=new Message();
        llmMessage.setContent(llmResponse);
        llmMessage.setChatId(message.getChatId());
        llmMessage.setCreatedAt(Instant.now());
        llmMessage.setCreatedByUserId("this is llm response");

        //todo Save response to the database


        messageRepository.save(llmMessage);

        return  llmMessage;
    }


}






