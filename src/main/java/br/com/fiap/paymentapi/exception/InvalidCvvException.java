package br.com.fiap.paymentapi.exception;

public class InvalidCvvException extends RuntimeException {
    public InvalidCvvException(String message) {
        super(message);
    }
}
