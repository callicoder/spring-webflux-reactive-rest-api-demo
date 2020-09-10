package com.example.webfluxdemo.contract;

import com.example.webfluxdemo.WebfluxDemoApplication;
import com.example.webfluxdemo.component.configuration.AutoWebTestClientConfiguration;
import com.example.webfluxdemo.integration.configuration.TestConfig;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebfluxDemoApplication.class)
@ActiveProfiles({ "contract" })
@AutoConfigureWebTestClient(timeout = "200000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {
    TestConfig.class,
    AutoWebTestClientConfiguration.class },
    initializers = ConfigFileApplicationContextInitializer.class)
public class ContractVerifierBase {

    @Autowired WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        RestAssuredWebTestClient.standaloneSetup(webTestClient.mutate());
    }
}
