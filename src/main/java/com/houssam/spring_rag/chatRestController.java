package com.houssam.spring_rag;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class chatRestController {
    private ChatClient chatClient;

    @Value("classpath:prompt/systemMessage.st")
    private Resource sysMessageResource;

    public chatRestController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(String question) {
        return chatClient.prompt()
                .user(question)
                .call().content();
    }
    @GetMapping(value = "/chat2", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chat2(String question) {
        return chatClient.prompt()
                .user(question)
                .stream().content();
    }

    @PostMapping(value = "/sentiment", produces = MediaType.TEXT_PLAIN_VALUE)
    public Sentiment sentiment(String review){
        return chatClient.prompt()
                .system(sysMessageResource)
                .user(review)
                .call().entity(Sentiment.class);
    }

}
