package com.bloodybank.bloodybank.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super("Could not find user with the email " + message);
    }
}
