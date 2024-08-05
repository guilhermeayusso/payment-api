package br.com.fiap.paymentapi.exception;

public class ExceedLimitException extends RuntimeException {
    public ExceedLimitException(String message) {
        super(message);
    }
}
