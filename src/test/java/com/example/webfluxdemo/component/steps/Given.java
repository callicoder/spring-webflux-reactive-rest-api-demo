package com.example.webfluxdemo.component.steps;

import com.example.webfluxdemo.model.Tweet;
import io.qameta.allure.Step;

public abstract class Given<T extends Given<T>> extends Base<T> {
    @Step
    public T givenIWhenATweet(final String tweetMsg){
        setTweet(new Tweet(tweetMsg));
        return getThis();
    }
}
