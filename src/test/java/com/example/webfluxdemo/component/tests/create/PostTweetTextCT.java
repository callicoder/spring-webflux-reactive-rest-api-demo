package com.example.webfluxdemo.component.tests.create;

import com.example.webfluxdemo.component.tests.BaseCT;
import com.example.webfluxdemo.tags.ComponentTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

@ComponentTest
@Feature("Component Tests - Post Tweet - text cases")
@Execution(ExecutionMode.CONCURRENT)
class PostTweetTextCT extends BaseCT {

    @ParameterizedTest(name = "post tweet text as - {0}")
    @ValueSource(strings = {
        "hello world",
        "text with special char !",
        "alphanumer1c w0w"
    })
    void postTweet(final String text) {
        getSteps()
            .givenIWhenATweet(text)
            .whenIPostTweet()
            .thenIShouldSeeSuccess()
            .thenISeeNonEmptyId();
    }

    @ParameterizedTest(name = "post tweet text as - {0}")
    @NullAndEmptySource
    void postTweetError(final String text) {
        getSteps()
            .givenIWhenATweet(text)
            .whenIPostTweet()
            .thenISeeStatusAs(HttpStatus.BAD_REQUEST);
    }

}
