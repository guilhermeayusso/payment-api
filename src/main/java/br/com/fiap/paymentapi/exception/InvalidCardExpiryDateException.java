package br.com.fiap.paymentapi.exception;


public class InvalidCardExpiryDateException extends RuntimeException {
    public InvalidCardExpiryDateException(String message) {
        super(message);
    }
}
