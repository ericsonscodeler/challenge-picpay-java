package br.com.ericson.picpay_challenge.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ericson.picpay_challenge.dto.TransferRequestDTO;
import br.com.ericson.picpay_challenge.service.TransferService;

@RestController
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/api/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransferRequestDTO request) {
        try {
            var result = TransferRequestDTO.builder()
                    .payer(request.getPayer())
                    .payee(request.getPayee())
                    .value(request.getValue())
                    .build();

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
