package br.com.ericson.picpay_challenge.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
