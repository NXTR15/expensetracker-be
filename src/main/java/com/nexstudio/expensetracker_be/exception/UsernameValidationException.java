package com.nexstudio.expensetracker_be.exception;

public class UsernameValidationException extends RuntimeException {
    public UsernameValidationException(String message) {
        super(message);
    }
}
