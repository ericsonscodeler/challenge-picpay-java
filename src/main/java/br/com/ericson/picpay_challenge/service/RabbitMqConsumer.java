package br.com.ericson.picpay_challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ericson.picpay_challenge.config.RabbitMqConfig;
import org.springframework.web.client.RestTemplate;

@Service
public class RabbitMqConsumer {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);

    private static final String NOTIFY_URL = "https://util.devi.tools/api/v1/notify";

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handleNotification(String message) {
        try {
            restTemplate.postForObject(NOTIFY_URL, null, String.class);
            logger.info("Notification sent successfully: {}", message);
        } catch (Exception e) {
            logger.error("Error sending notification: {}", e.getMessage(), e);
        }
    }
}
