package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

@SpringBootTest
class SpringBootAwsSqsLocalstackDemoApplicationTests {
    private static final String AWS_KEY = "test";
    private static final String FOO_Q = "foo_q";
    private static final String BAR_Q = "bar_q";
    private static final String BAZ_Q = "baz_q";

    static {
        final var credentials =
                AwsRequestOverrideConfiguration.builder()
                        .credentialsProvider(() -> AwsBasicCredentials.create(AWS_KEY, AWS_KEY))
                        .build();
        final var client =
                SqsAsyncClient.builder()
                        .endpointOverride(URI.create("http://127.0.0.1:4566"))
                        .region(Region.EU_WEST_1)
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
        try {
            createQueue(client, FOO_Q, credentials);
            createQueue(client, BAR_Q, credentials);
            createQueue(client, BAZ_Q, credentials);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
        Assertions.assertEquals(1, 1);
    }

    private static void createQueue(
            SqsAsyncClient client, String qName, AwsRequestOverrideConfiguration config)
            throws ExecutionException, InterruptedException {
        final var createdQueue =
                client.createQueue(
                                CreateQueueRequest.builder()
                                        .queueName(qName)
                                        .overrideConfiguration(config)
                                        .build())
                        .get();
        System.out.println(createdQueue.queueUrl());
    }
}
