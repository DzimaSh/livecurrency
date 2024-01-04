package com.livecurrency.exception;

public abstract class UnhandledException extends Exception {
    public UnhandledException(String message) {
        super(message);
    }
}
