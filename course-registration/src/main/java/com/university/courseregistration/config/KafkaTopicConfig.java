package com.university.courseregistration.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ENROLLMENT_CREATED_TOPIC = "enrollment-created";

    @Bean
    public NewTopic enrollmentCreatedTopic() {
        return TopicBuilder.name(ENROLLMENT_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}