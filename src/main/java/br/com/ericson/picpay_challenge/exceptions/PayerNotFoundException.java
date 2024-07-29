package br.com.ericson.picpay_challenge.exceptions;

public class PayerNotFoundException extends RuntimeException {

    public PayerNotFoundException() {
        super("Payer not found.");
    }
}
