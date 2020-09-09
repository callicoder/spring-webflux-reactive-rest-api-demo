package com.example.webfluxdemo.integration.tests;

import com.example.webfluxdemo.integration.client.DemoAppClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
public class BaseSIT {
    @Autowired
    private DemoAppClient demoAppClient;
}
