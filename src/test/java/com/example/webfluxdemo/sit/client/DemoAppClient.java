package com.example.webfluxdemo.sit.client;

import com.example.webfluxdemo.dto.RequestDTO;
import com.example.webfluxdemo.sit.configuration.TestConfig;
import com.example.webfluxdemo.utils.JsonUtils;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@Component
@RequiredArgsConstructor
public class DemoAppClient {

    private final WebTestClient sitWebTestClient;
    private final TestConfig    testConfig;

    public WebTestClient.ResponseSpec getAllTweets() {
        return getWebTestClientByBaseUrl()
            .get()
            .uri("tweets")
            .exchange();
    }

    public WebTestClient.ResponseSpec postTweet(final RequestDTO tweet) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());

        return
            getWebTestClientByBaseUrl()
                .post()
                .uri("tweets")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(JsonUtils.getJsonStringFromPojo(tweet)))
                .exchange();
    }

    private WebTestClient getWebTestClientByBaseUrl() {
        return sitWebTestClient
            .mutate()
            .baseUrl(testConfig.getWebFluxDemoAppUrl())
            .build();
    }
}
