package com.example.webfluxdemo.integration.steps;

@FunctionalInterface
public interface Step {
    void run();

    default Step then(Step after) {
        return () -> {
            this.run();
            after.run();
        };
    }
}
