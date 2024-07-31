package br.com.ericson.picpay_challenge.exceptions;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super("Saldo insuficiente");
    }
}
