package com.example.webfluxdemo.tags;

import com.example.webfluxdemo.integration.configuration.TestConfig;
import com.example.webfluxdemo.integration.configuration.CustomWebTestClientConfigurer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Tag("SystemIntegrationTest")
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestConfig.class, CustomWebTestClientConfigurer.class},
    initializers = ConfigFileApplicationContextInitializer.class)
public @interface SystemIntegrationTest {
}
