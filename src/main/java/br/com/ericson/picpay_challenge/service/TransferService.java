package br.com.ericson.picpay_challenge.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ericson.picpay_challenge.entity.UserEntity;
import br.com.ericson.picpay_challenge.exceptions.InsufficientBalanceException;
import br.com.ericson.picpay_challenge.exceptions.PayeeNotFoundException;
import br.com.ericson.picpay_challenge.exceptions.PayerNotFoundException;
import br.com.ericson.picpay_challenge.repositories.UserRepository;
import br.com.ericson.picpay_challenge.service.dto.TransferDTO;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<TransferDTO> moneyTransfer(Long payerId, Long payeeId, BigDecimal value) {

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

        TransferDTO transferDto = new TransferDTO(payeeId, payeeId, value);

        return ResponseEntity.ok(transferDto);
    }
}
