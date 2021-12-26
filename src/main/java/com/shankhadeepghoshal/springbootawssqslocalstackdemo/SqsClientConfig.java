package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

@Configuration
public class SqsClientConfig {
    private static final String AWS_KEY = "test";

    @Bean
    public SqsClient sqsAsyncClient() {
        final var client =
                SqsClient.builder()
                        .endpointOverride(URI.create("http://127.0.0.1:4566"))
                        .region(Region.US_EAST_1)
                        .credentialsProvider(
                                StaticCredentialsProvider.create(
                                        new AwsCredentials() {
                                            @Override
                                            public String accessKeyId() {
                                                return AWS_KEY;
                                            }

                                            @Override
                                            public String secretAccessKey() {
                                                return AWS_KEY;
                                            }
                                        }))
                        .build();
        createQueue(client, "foo_q");
        createQueue(client, "bar_q");
        createQueue(client, "baz_q");

        return client;
    }

    private static void createQueue(SqsClient client, String qName) {
        final var createdQueue =
                client.createQueue(CreateQueueRequest.builder().queueName(qName).build());
        System.out.println(String.join(" ", "Created queue with url:", createdQueue.queueUrl()));
    }
}
