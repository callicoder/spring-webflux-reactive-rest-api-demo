package com.example.webfluxdemo.component.tests.update;

import static java.util.Objects.requireNonNull;

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
@Feature("Component Tests - update Tweet by ID")
@Execution(ExecutionMode.CONCURRENT)
public class UpdateTweetCT extends BaseCT {

    private Tweet tweet;
    private Tweet newTweetData;

    @BeforeAll
    void dataSetUp() {
        tweet = getTweetRepository()
            .save(new Tweet("Initial Tweet"))
            .block();

        newTweetData = new Tweet("Updated Tweet");

        getSteps()
            .whenIUpdateTweet(requireNonNull(tweet).getId(), newTweetData)
            .thenTweetShouldBeUpdatedSuccessfully();
    }

    @Test
    @DisplayName("header must be application json")
    void shouldMatchHeader() {
        getSteps()
            .thenISeeValidResponseHeader();
    }

    @Test
    @DisplayName("text must be updated")
    void shouldMatchUpdatedText() {
        getSteps()
            .thenISeeTextInResponseAs(newTweetData.getText());
    }
}
