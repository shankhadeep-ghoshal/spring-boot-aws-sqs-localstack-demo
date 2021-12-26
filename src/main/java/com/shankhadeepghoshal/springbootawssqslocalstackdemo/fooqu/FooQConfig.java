package com.shankhadeepghoshal.springbootawssqslocalstackdemo.fooqu;

import static com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueUtils.createReceiveRequestDefaultSettingsFactory;

import com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueMessageSender;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Configuration
public class FooQConfig {
    @Bean
    @FooQ
    public Supplier<ReceiveMessageResponse> fooQuReceiver(
            @FooQ final String fooQueueUrl, final SqsClient client) {
        return () -> client.receiveMessage(createReceiveRequestDefaultSettingsFactory(fooQueueUrl));
    }

    @Bean
    @FooQ
    public QueueMessageSender fooQuSender(@FooQ final String fooQueueUrl, final SqsClient client) {
        return new QueueMessageSender(client, fooQueueUrl);
    }

    @Bean
    @FooQ
    public String getQueueUrlFoo(final SqsClient client) {
        return client.getQueueUrl(GetQueueUrlRequest.builder().queueName("foo_q").build())
                .queueUrl();
    }
}
