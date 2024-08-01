package br.com.ericson.picpay_challenge.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ericson.picpay_challenge.config.RabbitMqConfig;
import br.com.ericson.picpay_challenge.dto.AuthorizationResponseDTO;
import br.com.ericson.picpay_challenge.entity.UserEntity;
import br.com.ericson.picpay_challenge.exceptions.InsufficientBalanceException;
import br.com.ericson.picpay_challenge.exceptions.InvalidPayerException;
import br.com.ericson.picpay_challenge.exceptions.PayeeNotFoundException;
import br.com.ericson.picpay_challenge.exceptions.PayerNotFoundException;
import br.com.ericson.picpay_challenge.repositories.UserRepository;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @Autowired
    private EmailNotificationService emailNotificationService;

    public void moneyTransfer(UUID payerId, UUID payeeId, BigDecimal value) {

        UserEntity payer = userRepository.findById(payerId).orElseThrow(() -> {
            throw new PayerNotFoundException();
        });

        UserEntity payee = userRepository.findById(payeeId).orElseThrow(() -> {
            throw new PayeeNotFoundException();
        });

        if (!payer.getUserType().equals("PF")) {
            throw new InvalidPayerException();
        }

        if (payer.getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException();
        }

        AuthorizationResponseDTO authorizationResponse = authorizationService.checkAuthorization();

        if (!authorizationResponse.getStatus()) {
            String errorMessage = "Transferência não autorizada!";
            rabbitMqProducer.sendMessage(RabbitMqConfig.QUEUE_NAME, errorMessage);

            throw new RuntimeException(authorizationResponse.getMessage());
        }

        String notificationMessage = "Transferência de " + value + " de " + payerId + " para " + payeeId
                + " concluída com sucesso.";

        System.out.println(notificationMessage);
        rabbitMqProducer.sendMessage(RabbitMqConfig.QUEUE_NAME, notificationMessage);

        userRepository.save(payer);
        userRepository.save(payee);

        // Enviar emails de notificação
        String payerEmail = payer.getEmail();
        String payeeEmail = payee.getEmail();
        String emailSubject = "Notificação de Transferência";
        String emailBody = "Transferência de " + value + " de " + payer.getName() + " para " + payee.getName()
                + " concluída com sucesso.";

        emailNotificationService.sendEmail(payerEmail, emailSubject, emailBody);
        emailNotificationService.sendEmail(payeeEmail, emailSubject, emailBody);
    }
}
