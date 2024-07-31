package br.com.ericson.picpay_challenge.exceptions;

public class InvalidPayerException extends RuntimeException {
    public InvalidPayerException() {
        super("Apenas pessoa física pode fazer a transferência");
    }
}
