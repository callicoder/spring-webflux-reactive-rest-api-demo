package com.example.webfluxdemo.component.tests;

import com.example.webfluxdemo.component.steps.ComponentTestSteps;
import com.example.webfluxdemo.repository.TweetRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class BaseCT {

    @Autowired
    private ComponentTestSteps steps;

    @Autowired
    private TweetRepository tweetRepository;

}
