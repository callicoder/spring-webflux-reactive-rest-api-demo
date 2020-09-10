package com.example.webfluxdemo.component.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.webfluxdemo.model.Tweet;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.qameta.allure.Step;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public abstract class Then<T extends Then<T>> extends When<T> {

    @Step
    public T thenIShouldSeeSuccess() {
        getResponse()
            .expectStatus()
            .isOk();

        setTweetEntityExchangeResult(getResponse()
            .expectBody(Tweet.class)
            .returnResult());

        setTweetResponseDTO(getTweetEntityExchangeResult().getResponseBody());
        return getThis();
    }

    @Step
    public T thenTweetsShouldBeRetrievedSuccessfully() {
        getResponse()
            .expectStatus()
            .isOk();

        setTweetsEntityExchangeResult(getResponse()
            .expectBodyList(Tweet.class)
            .returnResult());

        setTweetsResponseDTO(getTweetsEntityExchangeResult().getResponseBody());
        return getThis();
    }

    @Step
    public T thenTweetShouldBeRetrievedSuccessfully() {
        getResponse()
            .expectStatus()
            .isOk();

        setTweetEntityExchangeResult(getResponse()
            .expectBody(Tweet.class)
            .returnResult());

        setTweetResponseDTO(getTweetEntityExchangeResult().getResponseBody());
        return getThis();
    }

    @Step
    public T thenTweetShouldBeUpdatedSuccessfully() {
        getResponse()
            .expectStatus()
            .isOk();

        setTweetEntityExchangeResult(getResponse()
            .expectBody(Tweet.class)
            .returnResult());

        setTweetResponseDTO(getTweetEntityExchangeResult().getResponseBody());
        return getThis();
    }

    @Step
    public T thenIShouldSeeTweetDeletedSuccessfully() {

        getResponse()
            .expectStatus()
            .isOk();
        return getThis();
    }

    @Step
    public void thenISeeNonEmptyId() {
        assertNotEquals(StringUtils.EMPTY, Objects
            .requireNonNull(getTweetResponseDTO())
            .getId());
    }

    @Step
    public void thenIdShouldBeAlphaNumeric() {
        assertTrue(Objects
            .requireNonNull(getTweetResponseDTO())
            .getId()
            .matches("^[a-zA-Z0-9]+$"));
    }

    @Step
    public T thenISeeValidResponseHeader() {
        assertEquals(HttpHeaderValues.APPLICATION_JSON.toString(), Objects
            .requireNonNull(getTweetEntityExchangeResult()
                .getResponseHeaders()
                .get(HttpHeaders.CONTENT_TYPE))
            .get(0));
        return getThis();
    }

    @Step
    public void thenISeeTextInResponseAs(String tweetMessage) {
        assertEquals(tweetMessage, Objects
            .requireNonNull(getTweetResponseDTO())
            .getText());
    }

    @Step
    public void thenCreatedAtShouldBeNonNull() {
        assertNotNull(Objects
            .requireNonNull(getTweetResponseDTO())
            .getCreatedAt());
    }

    @Step
    public void thenTweetsListShouldBeNonEmpty() {
        assertTrue(Objects
            .requireNonNull(getTweetsResponseDTO())
            .size() > 0);
    }

    @Step
    public void thenPostedTweetShouldBeRetrieved(final Tweet postedTweet) {
        assertTrue(getTweetsResponseDTO()
            .stream()
            .anyMatch(tweet -> tweet
                .getId()
                .equals(postedTweet.getId())));
    }

    @Step
    public void thenISeeStatusAs(HttpStatus status) {
        getResponse()
            .expectStatus()
            .isEqualTo(status)
        ;
    }

    @Step
    public void thenIdShouldMatchWithPostResponse(final Tweet tweet) {
        assertEquals(tweet.getId(), getTweetResponseDTO().getId());
    }

    @Step
    public void thenCreatedAtShouldMatchWithPostResponse(final Tweet tweet) {
        assertEquals(tweet.getCreatedAt(), getTweetResponseDTO().getCreatedAt());
    }

    @Step
    public void thenTextShouldMatchWithPostResponse(final Tweet tweet) {
        assertEquals(tweet.getText(), getTweetResponseDTO().getText());
    }
}
