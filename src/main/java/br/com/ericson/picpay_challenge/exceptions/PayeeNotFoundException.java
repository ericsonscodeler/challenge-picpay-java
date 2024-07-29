package br.com.ericson.picpay_challenge.exceptions;

public class PayeeNotFoundException extends RuntimeException {

    public PayeeNotFoundException() {
        super("Payee not found.");
    }
}
