package com.example.webfluxdemo.component.tests.delete;

import com.example.webfluxdemo.component.tests.BaseCT;
import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.tags.ComponentTest;
import org.junit.jupiter.api.Test;

@ComponentTest
public class DeleteTweetCT extends BaseCT {

    @Test
    public void testDeleteTweet() {
        Tweet tweet = getTweetRepository()
            .save(new Tweet("To be deleted"))
            .block();

        getSteps()
            .whenIDeleteATweet(tweet.getId())
            .thenIShouldSeeTweetDeletedSuccessfully();
    }
}
