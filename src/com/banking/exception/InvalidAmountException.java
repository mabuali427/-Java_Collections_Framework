package com.banking.exception;

/**
 * Exception thrown when an invalid amount is provided for a transaction.
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
