package com.example.webfluxdemo.contract;

import com.example.webfluxdemo.tags.ComponentTest;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@ComponentTest
public class ContractVerifierBase {

    @Autowired WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        RestAssuredWebTestClient.standaloneSetup(webTestClient.mutate());
    }
}
