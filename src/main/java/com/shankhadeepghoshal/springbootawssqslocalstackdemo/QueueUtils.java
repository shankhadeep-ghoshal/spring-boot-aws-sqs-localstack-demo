package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class QueueUtils {
    public static ReceiveMessageRequest createReceiveRequestDefaultSettingsFactory(String qUrl) {
        return createReceiveRequestWithStatedNumOfMessagesFactory(qUrl, 10);
    }

    @SuppressWarnings("SameParameterValue")
    public static ReceiveMessageRequest createReceiveRequestWithStatedNumOfMessagesFactory(
            String qUrl, Integer numMessages) {
        return ReceiveMessageRequest.builder()
                .queueUrl(qUrl)
                .maxNumberOfMessages(numMessages)
                .build();
    }
}
