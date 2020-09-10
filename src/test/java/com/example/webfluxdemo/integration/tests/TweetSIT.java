package com.example.webfluxdemo.integration.tests;

import com.example.webfluxdemo.integration.steps.SitSteps;
import com.example.webfluxdemo.tags.SystemIntegrationTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@SystemIntegrationTest
@Feature("Tweet App Integration tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TweetSIT {

    @Autowired
    private SitSteps sitSteps;

    @Test
    @DisplayName("post tweet")
    @Order(1)
    void shouldGetSuccessOnPost() {
        sitSteps.postTweet
            .then(sitSteps.validateResponseStatus)
            .then(sitSteps.storeTweetId).run();
    }

    @Test
    @DisplayName("get all tweets")
    @Order(2)
    void shouldGetSuccessOnGetAll() {
        sitSteps.retrieveTweets
            .then(sitSteps.validateResponseStatus).run();
    }

    @Test
    @DisplayName("get tweet by id")
    @Order(3)
    void shouldGetSuccessOnGet() {
        sitSteps.retrieveTweetById
            .then(sitSteps.validateResponseStatus).run();
    }

    @Test
    @DisplayName("update tweet")
    @Order(4)
    void shouldGetSuccessOnUpdate() {
        sitSteps.updateTweet
            .then(sitSteps.validateResponseStatus).run();
    }

    @Test
    @DisplayName("delete tweet")
    @Order(5)
    void shouldGetSuccessOnDelete() {
        sitSteps.deleteTweet
            .then(sitSteps.validateResponseStatus).run();
    }
}
