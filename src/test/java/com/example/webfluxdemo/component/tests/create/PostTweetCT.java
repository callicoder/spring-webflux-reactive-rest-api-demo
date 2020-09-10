package com.example.webfluxdemo.component.tests.create;

import com.example.webfluxdemo.component.tests.BaseCT;
import com.example.webfluxdemo.tags.ComponentTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@ComponentTest
@Feature("Component Tests - Post Tweet")
@Execution(ExecutionMode.CONCURRENT)
class PostTweetCT extends BaseCT {

    private static final String TWEET_MESSAGE = "This is a Test Tweet";

    @BeforeAll
    void postTweet() {
        getSteps()
            .givenIWhenATweet(TWEET_MESSAGE)
            .whenIPostTweet()
            .thenIShouldSeeSuccess();
    }

    @Test
    @DisplayName("content type should be application/json")
    void testCreateTweet() {
        getSteps()
            .thenISeeValidResponseHeader();
    }

    @Test
    @DisplayName("tweet id should be non empty")
    void shouldGetTweetIdNonNull() {
        getSteps()
            .thenISeeNonEmptyId();
    }

    @Test
    @DisplayName("tweet id should be alphanumeric")
    void shouldGetTweetIdAlphaNumeric() {
        getSteps()
            .thenIdShouldBeAlphaNumeric();
    }

    @Test
    @DisplayName("text must match as passed in payload")
    void shouldGetSameTextAsPassedInPayload(){
        getSteps()
            .thenISeeTextInResponseAs(TWEET_MESSAGE);
    }

    @Test
    @DisplayName("createdAt must be non null")
    void shouldGetNonNullCreatedAt(){
        getSteps()
            .thenCreatedAtShouldBeNonNull();
    }

}
