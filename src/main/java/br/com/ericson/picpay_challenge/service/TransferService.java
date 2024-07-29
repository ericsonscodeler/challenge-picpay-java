package br.com.ericson.picpay_challenge.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ericson.picpay_challenge.dto.TransferResponseDTO;
import br.com.ericson.picpay_challenge.entity.UserEntity;
import br.com.ericson.picpay_challenge.exceptions.InsufficientBalanceException;
import br.com.ericson.picpay_challenge.exceptions.PayeeNotFoundException;
import br.com.ericson.picpay_challenge.exceptions.PayerNotFoundException;
import br.com.ericson.picpay_challenge.repositories.UserRepository;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<TransferResponseDTO> moneyTransfer(UUID payerId, UUID payeeId, BigDecimal value) {

        UserEntity payer = userRepository.findById(payerId).orElseThrow(() -> {
            throw new PayerNotFoundException();
        });

        UserEntity payee = userRepository.findById(payeeId).orElseThrow(() -> {
            throw new PayeeNotFoundException();
        });

        if (payer.getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException();
        }

        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        userRepository.save(payer);
        userRepository.save(payee);

        var transferDto = TransferResponseDTO.builder()
                .payer(payerId)
                .payee(payeeId)
                .value(value)
                .build();

        return ResponseEntity.ok(transferDto);
    }
}
