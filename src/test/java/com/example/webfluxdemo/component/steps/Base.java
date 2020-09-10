package com.example.webfluxdemo.component.steps;

import com.example.webfluxdemo.model.Tweet;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
@Setter
public abstract class Base<T extends Base<T>> {
    private Tweet                      tweet;
    private WebTestClient.ResponseSpec response;

    private Tweet       tweetResponseDTO;
    private List<Tweet> tweetsResponseDTO;

    private EntityExchangeResult<Tweet>       tweetEntityExchangeResult;
    private EntityExchangeResult<List<Tweet>> tweetsEntityExchangeResult;

    @Autowired
    private WebTestClient webTestClient;

    protected abstract T getThis();
}
