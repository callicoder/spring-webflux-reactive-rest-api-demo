package com.example.webfluxdemo.integration.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ComponentScan(basePackages = {
    "com.example.webfluxdemo.integration.client",
    "com.example.webfluxdemo.integration.steps"
})
@TestComponent
public class TestConfig {
    @Value("${client.web-flux-demo-app.base-url:#{null}}")
    private String webFluxDemoAppUrl;
}
