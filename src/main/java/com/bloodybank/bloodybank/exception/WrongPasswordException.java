package com.bloodybank.bloodybank.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong password");
    }
}
