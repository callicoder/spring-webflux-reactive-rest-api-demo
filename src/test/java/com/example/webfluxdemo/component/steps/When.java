package com.example.webfluxdemo.component.steps;

import com.example.webfluxdemo.model.Tweet;
import io.qameta.allure.Step;
import java.util.Collections;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public abstract class When<T extends When<T>> extends Given<T> {

    @Step
    public T whenIPostTweet() {
        setResponse(getWebTestClient()
            .post()
            .uri("/tweets")
            .body(Mono.just(getTweet()), Tweet.class)
            .exchange());
        return getThis();
    }

    @Step
    public T whenIGetAllTweets() {
        setResponse(getWebTestClient()
            .get()
            .uri("/tweets")
            .exchange());
        return getThis();
    }

    @Step
    public T whenIGetTweetById(final String tweetId) {
        setResponse(getWebTestClient()
            .get()
            .uri(String.format("/tweets/%s", tweetId))
            .exchange());
        return getThis();
    }

    @Step
    public T whenIUpdateTweet(String tweetId, Tweet tweetData) {

        setResponse(getWebTestClient()
            .put()
            .uri("/tweets/{id}", Collections.singletonMap("id", tweetId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(tweetData), Tweet.class)
            .exchange());

        return getThis();
    }

    @Step
    public T whenIDeleteATweet(String tweetId) {

        setResponse(getWebTestClient()
            .delete()
            .uri("/tweets/{id}", Collections.singletonMap("id", tweetId))
            .exchange());
        return getThis();
    }
}
