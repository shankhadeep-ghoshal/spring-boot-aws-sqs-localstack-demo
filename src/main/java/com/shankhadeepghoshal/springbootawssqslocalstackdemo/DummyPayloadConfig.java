package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyPayloadConfig {
    @Bean
    @Payload1
    public Supplier<Payload> getPayload() {
        return () -> new Payload(ThreadLocalRandom.current().nextInt(), "message");
    }
}
