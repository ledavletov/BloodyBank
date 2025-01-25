package com.bloodybank.bloodybank.exception;

public class NoTransactionException extends Exception {
    public NoTransactionException(String message) {
        super("There is no transaction with the id " + message);
    }
}
