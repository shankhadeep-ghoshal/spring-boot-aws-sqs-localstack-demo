package com.shankhadeepghoshal.springbootawssqslocalstackdemo.bazqu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shankhadeepghoshal.springbootawssqslocalstackdemo.Payload;
import com.shankhadeepghoshal.springbootawssqslocalstackdemo.Payload1;
import com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueMessageSender;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@EnableScheduling
@Slf4j
public class BazQProcessor {
    @BazQ Supplier<ReceiveMessageResponse> supplyMessageResponse;

    @BazQ QueueMessageSender messageSender;

    @Payload1 Supplier<Payload> supplyPayload;

    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.SECONDS)
    public void sendMessage() throws JsonProcessingException {
        messageSender.messageResponse(objectMapper.writeValueAsString(supplyPayload.get()));
    }

    @Scheduled(fixedRate = 1, initialDelay = 0)
    public void processQueueMessage() {
        final var messages = supplyMessageResponse.get().messages();
        if (null != messages && !messages.isEmpty()) {
            messages.forEach(message -> log.info("Received data in BazQ {}", message.body()));
        }
    }
}
