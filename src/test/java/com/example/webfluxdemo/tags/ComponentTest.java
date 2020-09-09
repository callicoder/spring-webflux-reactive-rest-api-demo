package com.example.webfluxdemo.tags;

import com.example.webfluxdemo.WebfluxDemoApplication;
import com.example.webfluxdemo.component.configuration.AutoWebTestClientConfiguration;
import com.example.webfluxdemo.integration.configuration.TestConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Tag("ComponentTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebfluxDemoApplication.class)
@ActiveProfiles({ "component" })
@AutoConfigureWebTestClient(timeout = "200000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { TestConfig.class, AutoWebTestClientConfiguration.class },
    initializers = ConfigFileApplicationContextInitializer.class)
public @interface ComponentTest {
}
