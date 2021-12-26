package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@lombok.Value
public class QueueMessageSender {

    SqsClient client;
    String queueUrl;

    public void messageResponse(final String message) {
        final var senderRequest =
                SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build();
        client.sendMessage(senderRequest);
    }
}
