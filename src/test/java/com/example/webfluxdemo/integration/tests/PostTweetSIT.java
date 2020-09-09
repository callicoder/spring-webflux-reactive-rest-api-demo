package com.example.webfluxdemo.integration.tests;

import com.example.webfluxdemo.domain.dto.RequestDTO;
import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.tags.SystemIntegrationTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

@SystemIntegrationTest
@Feature("Web Flux Demo App - Deployed Environment Test")
public class PostTweetSIT extends BaseSIT {

    private static final String TWEET_MESSAGE = "This is a Tweet For Test";

    private WebTestClient.ResponseSpec response;
    private Tweet                      responseBody;

    @BeforeAll
    void init() {
        response = getDemoAppClient()
            .postTweet(RequestDTO
                .builder()
                .text(TWEET_MESSAGE)
                .build());

        response
            .expectStatus()
            .isOk();

        responseBody = response
            .expectBody(Tweet.class)
            .returnResult()
            .getResponseBody();
    }

    @Test
    @DisplayName("validate tweet message")
    void tweetMessageShouldBeSame() {
        Assertions
            .assertEquals(TWEET_MESSAGE, responseBody.getText());
    }

    @Test
    @DisplayName("validate non null create time")
    void shouldReturnNonNullCreateDate() {
        Assertions
            .assertNotNull(responseBody.getCreatedAt());
    }

}
