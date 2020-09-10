package com.example.webfluxdemo.utils;

import java.util.Arrays;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;

public interface GenericRetryUtils {

    @Slf4j
    class LogHolder {
    }

    @SafeVarargs
    static <T> T retry(final Callable<T> callable, final int maxRetries, final long millis,
        final Class<? extends Exception>... exceptionClasses) {

        final Class<? extends Exception>[] classes = exceptionClasses.length == 0 ? new Class[] { Exception.class } : exceptionClasses;

        int retryCount = 0;
        Exception lastException;

        while (retryCount < maxRetries) {
            try {
                return callable.call();
            }
            catch (Exception e) {
                lastException = e;
                try {
                    Thread.sleep(millis);
                }
                catch (InterruptedException ex) {
                    LogHolder.log.error("InterruptedException ", ex);
                }
                if (Arrays
                    .stream(classes)
                    .noneMatch(tClass -> tClass.isAssignableFrom(e.getClass()))) {
                    LogHolder.log.error("FAILED - Command failed", lastException);
                    throw new RuntimeException(lastException);
                } else {
                    retryCount++;
                    LogHolder.log.error("FAILED - Command failed on retry " + retryCount + " of " + maxRetries, e);
                }

                if (retryCount >= maxRetries) {
                    break;
                }
            }
        }
        throw new RuntimeException("Command failed on all of " + maxRetries + " retries");
    }
}
