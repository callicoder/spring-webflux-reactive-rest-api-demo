package com.example.webfluxdemo.integration.steps;

import com.example.webfluxdemo.domain.dto.RequestDTO;
import com.example.webfluxdemo.integration.client.DemoAppClient;
import com.example.webfluxdemo.model.Tweet;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestComponent
public class SitSteps {
    @Autowired
    private DemoAppClient demoAppClient;

    private WebTestClient.ResponseSpec response;
    private String                     tweetId;

    public Step postTweet = () -> response = demoAppClient
        .postTweet(RequestDTO
            .builder()
            .text("Tweet Text")
            .build());

    public Step validateResponseStatus = () -> response
        .expectStatus()
        .isOk();

    public Step storeTweetId = () -> tweetId = Objects
        .requireNonNull(response
            .expectBody(Tweet.class)
            .returnResult()
            .getResponseBody())
        .getId();

    public Step retrieveTweets = () -> response = demoAppClient
        .getAllTweets();

    public Step retrieveTweetById = () -> response = demoAppClient
        .getTweetById(tweetId);

    public Step updateTweet = () -> response = demoAppClient
        .updateTweet(tweetId, RequestDTO
            .builder()
            .text("Updated Tweet Text")
            .build());

    public Step deleteTweet = () -> response = demoAppClient
        .deleteTweet(tweetId);
}
