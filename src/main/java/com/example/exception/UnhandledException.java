package com.example.exception;

public abstract class UnhandledException extends Exception {
    public UnhandledException(String message) {
        super(message);
    }
}
