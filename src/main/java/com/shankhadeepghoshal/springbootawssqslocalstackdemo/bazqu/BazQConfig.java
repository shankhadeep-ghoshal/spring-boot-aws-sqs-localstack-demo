package com.shankhadeepghoshal.springbootawssqslocalstackdemo.bazqu;

import static com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueUtils.createReceiveRequestDefaultSettingsFactory;

import com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueMessageSender;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Configuration
public class BazQConfig {
    @Bean
    @BazQ
    public QueueMessageSender bazQuSender(@BazQ final String bazQueueUrl, final SqsClient client) {
        return new QueueMessageSender(client, bazQueueUrl);
    }

    @Bean
    @BazQ
    public Supplier<ReceiveMessageResponse> bazQuReceiver(
            @BazQ final String bazQueueUrl, final SqsClient client) {
        return () -> client.receiveMessage(createReceiveRequestDefaultSettingsFactory(bazQueueUrl));
    }

    @Bean
    @BazQ
    public String getQueueUrlBaz(final SqsClient client) {
        return client.getQueueUrl(GetQueueUrlRequest.builder().queueName("baz_q").build())
                .queueUrl();
    }
}
