package com.example.exception;

public class UnhandledCommandException extends Exception {
    public UnhandledCommandException(String message) {
        super(message);
    }
}
