package br.com.ericson.picpay_challenge.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ericson.picpay_challenge.config.RabbitMqConfig;

@Service
public class RabbitMqConsumer {

    private static final String NOTIFY_URL = "https://util.devi.tools/api/v1/notify";

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handleNotification(String message) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(NOTIFY_URL, null, String.class);
            System.out.println("Notificação enviada com sucesso: " + message);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
