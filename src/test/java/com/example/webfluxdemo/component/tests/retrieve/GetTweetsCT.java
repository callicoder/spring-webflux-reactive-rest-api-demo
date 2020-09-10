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
@Feature("Component Tests - Get Tweets")
@Execution(ExecutionMode.CONCURRENT)
class GetTweetsCT extends BaseCT {

    private static final String TWEET_MESSAGE = "This is a Test Tweet";
    private              Tweet  tweet;

    @BeforeAll
    void getTweets() {
        tweet = getTweetRepository()
            .save(new Tweet(TWEET_MESSAGE))
            .block();

        getSteps()
            .whenIGetAllTweets()
            .thenTweetsShouldBeRetrievedSuccessfully();
    }

    @Test
    @DisplayName("list should be non empty")
    void shouldReturnNonEmptyResponse() {
        getSteps()
            .thenTweetsListShouldBeNonEmpty();
    }

    @Test
    @DisplayName("tweet list must contain posted tweet")
    void shouldRetrievePostedTweet() {
        getSteps()
            .thenPostedTweetShouldBeRetrieved(tweet);
    }

}
