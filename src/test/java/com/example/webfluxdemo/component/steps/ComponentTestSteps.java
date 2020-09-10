package com.example.webfluxdemo.component.steps;

import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ComponentTestSteps extends Then<ComponentTestSteps> {

    @Override protected ComponentTestSteps getThis() {
        return this;
    }
}
