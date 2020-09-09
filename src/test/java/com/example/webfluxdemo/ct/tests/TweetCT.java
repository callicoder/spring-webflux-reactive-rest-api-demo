package com.example.webfluxdemo.ct.tests;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.tags.ComponentTest;
import io.qameta.allure.Feature;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ComponentTest
@Feature("Component Tests - Get Tweet")
public class GetTweetCT extends BaseCT {

    private static final String                     TWEET_MESSAGE = "This is a Test Tweet";
    private              WebTestClient.ResponseSpec response;

    @Test
    @DisplayName("create tweet tests")
    public void testCreateTweet() {

        Tweet tweet = new Tweet(TWEET_MESSAGE);

        response = getWebTestClient()
            .post()
            .uri("/tweets")
            .body(Mono.just(tweet), Tweet.class)
            .exchange();

        response
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .isNotEmpty()
            .jsonPath("$.text")
            .isEqualTo(TWEET_MESSAGE);
    }

    @Test
    @DisplayName("get all tweets")
    public void testGetAllTweets() {
        response = getWebTestClient()
            .get()
            .uri("/tweets")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        response
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Tweet.class);
    }

    @Test
    public void testGetSingleTweet() {
        Tweet tweet = tweetRepository
            .save(new Tweet("Hello, World!"))
            .block();

        response = getWebTestClient()
            .get()
            .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
            .exchange();

        response
            .expectStatus()
            .isOk()
            .expectBody()
            .consumeWith(response ->
                Assertions
                    .assertThat(response.getResponseBody())
                    .isNotNull());
    }
}
