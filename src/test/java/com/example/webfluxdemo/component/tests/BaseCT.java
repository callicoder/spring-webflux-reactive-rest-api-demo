package com.example.webfluxdemo.component.tests;

import com.example.webfluxdemo.repository.TweetRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
public class BaseCT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TweetRepository tweetRepository;
}
