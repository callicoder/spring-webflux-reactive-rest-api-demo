package com.example.webfluxdemo.sit.tests;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.tags.SystemIntegrationTest;
import io.qameta.allure.Feature;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

@SystemIntegrationTest
@Feature("Web Flux Demo App - Deployed Environment Test")
@RequiredArgsConstructor
public class WebFluxDemoAppGetSIT extends BaseSIT {

    private WebTestClient.ResponseSpec response;
    List<Tweet> responseBody;

    @BeforeAll
    void callService() {
        response = getDemoAppClient()
            .getAllTweets();
    }

    @Test
    @DisplayName("should get success with GET /tweets call")
    public void shouldGetStatusOk() {
        response
            .expectStatus()
            .isOk();
    }

    @Test
    @DisplayName("tweets count should be > 0")
    void tweetCountShouldBeGreaterThanZero() {
        responseBody = response
            .expectBodyList(Tweet.class)
            .returnResult()
            .getResponseBody();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.size() > 0);
    }
}
