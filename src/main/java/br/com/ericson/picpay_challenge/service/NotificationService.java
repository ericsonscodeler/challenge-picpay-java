package br.com.ericson.picpay_challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ericson.picpay_challenge.config.RabbitMqConfig;

@Service
public class NotificationService {

    @Autowired
    private RabbitMqProducer rabbitMQProducer;

    public void sendNotification(String message) {
        rabbitMQProducer.sendMessage(RabbitMqConfig.QUEUE_NAME, message);
    }
}