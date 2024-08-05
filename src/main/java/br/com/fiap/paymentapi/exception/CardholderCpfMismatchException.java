package br.com.fiap.paymentapi.exception;

public class CardholderCpfMismatchException extends RuntimeException {
    public CardholderCpfMismatchException(String message) {
        super(message);
    }
}
