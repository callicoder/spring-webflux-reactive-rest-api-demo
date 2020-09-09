package com.example.webfluxdemo.ct.configuration;

import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.MockServerConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestConfiguration
public class AutoWebTestClientConfiguration {
    @Bean
    public WebTestClient webTestClient(final ApplicationContext applicationContext,
        final List<WebTestClientBuilderCustomizer> customizers, final List<MockServerConfigurer> configurers) {
        WebTestClient.MockServerSpec<?> mockServerSpec = WebTestClient.bindToApplicationContext(applicationContext);
        for (MockServerConfigurer configurer: configurers) {
            mockServerSpec.apply(configurer);
        }
        WebTestClient.Builder builder = mockServerSpec.configureClient();
        for(WebTestClientBuilderCustomizer customizer: customizers) {
            customizer.customize(builder);
        }
        return builder.build();
    }
}
