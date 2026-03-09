package app.chat.springapi.services;

import app.chat.springapi.models.Message;
import app.chat.springapi.models.dtos.completions.ChatCompletionResponse;
import app.chat.springapi.models.dtos.completions.ResponseMessage;
import app.chat.springapi.models.dtos.messages.RequestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MessageResponseService {
    private String apiKey;
    public MessageResponseService(@Value("${llms.groq.key}") String apiKey) {
            this.apiKey = apiKey;
        }

    public String getMessageResponse() {

    //headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+apiKey);

    //request body
        RequestMessage requestBody = new RequestMessage();

    //the request entity is the body and the headers
        HttpEntity request=new HttpEntity<>(requestBody,headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity response=restTemplate.postForEntity(
                "http://localhost:8080/messages",
                request,
                Message.class);
        Message responseBody=(Message) response.getBody();


        return responseBody.getContent();






    }
}