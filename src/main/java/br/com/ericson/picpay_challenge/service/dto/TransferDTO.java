package br.com.ericson.picpay_challenge.service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {

    private Long payerId;
    private Long payee;
    private BigDecimal value;
}
