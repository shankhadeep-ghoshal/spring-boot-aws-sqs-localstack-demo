package com.shankhadeepghoshal.springbootawssqslocalstackdemo.barqu;

import static com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueUtils.createReceiveRequestDefaultSettingsFactory;

import com.shankhadeepghoshal.springbootawssqslocalstackdemo.QueueMessageSender;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Configuration
public class BarQConfig {
    @Bean
    @BarQ
    public Supplier<ReceiveMessageResponse> barQuReceiver(
            @BarQ final String barQueueUrl, final SqsClient client) {
        return () -> client.receiveMessage(createReceiveRequestDefaultSettingsFactory(barQueueUrl));
    }

    @Bean
    @BarQ
    public QueueMessageSender barQuSender(@BarQ final String barQueueUrl, final SqsClient client) {
        return new QueueMessageSender(client, barQueueUrl);
    }

    @Bean
    @BarQ
    public String getQueueUrlBar(final SqsClient client) {
        return client.getQueueUrl(GetQueueUrlRequest.builder().queueName("bar_q").build())
                .queueUrl();
    }
}
