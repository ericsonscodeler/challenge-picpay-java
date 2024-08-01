package br.com.ericson.picpay_challenge.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_NAME = "notificationQueue";

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
