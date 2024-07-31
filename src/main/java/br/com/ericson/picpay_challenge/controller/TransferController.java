package br.com.ericson.picpay_challenge.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ericson.picpay_challenge.dto.TransferRequestDTO;
import br.com.ericson.picpay_challenge.dto.TransferResponseDTO;
import br.com.ericson.picpay_challenge.service.TransferService;

@RestController
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/api/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransferRequestDTO request) {
        try {
            this.transferService.moneyTransfer(request.getPayer(), request.getPayee(), request.getValue());

            return ResponseEntity.ok().body(new TransferResponseDTO<>("Transferência realizada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransferResponseDTO<>(e.getMessage()));
        }
    }
}
