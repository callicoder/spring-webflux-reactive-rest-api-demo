package com.example.webfluxdemo.component.tests.retrieve;

import com.example.webfluxdemo.component.tests.BaseCT;
import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.tags.ComponentTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@ComponentTest
@Feature("Component Tests - Get Tweet by ID")
@Execution(ExecutionMode.CONCURRENT)
class GetTweetByIdCT extends BaseCT {

    private static final String TWEET_MESSAGE = "This is a Test Tweet";

    private Tweet tweet;

    @BeforeAll
    void postTweet() {

        tweet = getTweetRepository()
            .save(new Tweet(TWEET_MESSAGE))
            .block();

        getSteps()
            .whenIGetTweetById(tweet.getId())
            .thenTweetShouldBeRetrievedSuccessfully();
    }

    @Test
    @DisplayName("id must match as received in post response")
    void shouldMatchIdWithPostResponse() {
        getSteps()
            .thenIdShouldMatchWithPostResponse(tweet);
    }

    @Test
    @DisplayName("text must match as received in post response")
    void shouldMatchTextWithPostResponse() {
        getSteps()
            .thenTextShouldMatchWithPostResponse(tweet);
    }

    @Test
    @DisplayName("createdAt must match as received in post response")
    void shouldMatchCreatedAtWithPostResponse() {
        getSteps()
            .thenCreatedAtShouldMatchWithPostResponse(tweet);
    }

}
