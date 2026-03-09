package app.chat.springapi.services;

import app.chat.springapi.models.dtos.completions.ChatCompletionResponse;
import app.chat.springapi.models.dtos.completions.ContentPart;
import app.chat.springapi.models.dtos.completions.MessageDTO;
import app.chat.springapi.models.dtos.completions.RequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.net.http.HttpRequest;
import java.util.List;

@Service

public class CompletionApiService {
// inject api key from application.properties
    private String apiKey;

    public CompletionApiService(@Value("${llms.groq.key}") String apiKey) {
        this.apiKey = apiKey;
    }
//write a method that takes a prompt and returns a completion from openai api
    public String getCompletion(String prompt) {
        // This method should interact with OpenAI's API to get a completion for the given prompt.
        //for now we return a placeholder string


//-------------------------REQUEST-----------------------------
        // HEADERS+BODY = REQUEST
        //To construct the request we need to build the headers and the body as it is
        //in the openai api documentation /api.groq.com/openai/v1/chat/completions

        // build HEADERS of the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+apiKey);


//CREATE THE REQUEST BODY CONSISTS OF MODEL AND MESSAGES, MESSAGES CONSISTS OF ROLE AND CONTENT, CONTENT CONSISTS OF TYPE AND TEXT
        RequestDTO requestBody = new RequestDTO("llama-3.1-8b-instant",List.of(
                new MessageDTO(
                        "user",
                        List.of(
                                new ContentPart("text",prompt)))));

//the request entity is the body and the headers
        HttpEntity request=new HttpEntity<>(requestBody,headers);

//Parameters are the url, the request , and the TYPE OF THE response  of the api we expect

        //http client for spring is called RestTemplate...is similar to axios in js
        RestTemplate restTemplate = new RestTemplate();
            //Response is the response we get from the api with request
            // and class type
        ResponseEntity response=restTemplate.postForEntity(
                "https://api.groq.com/openai/v1/chat/completions",
                request,
                ChatCompletionResponse.class);

//response body is the actual data we want
        ChatCompletionResponse responseBody=(ChatCompletionResponse) response.getBody();


        return responseBody.getChoices().get(0).getMessage().getContent();
    }
}
