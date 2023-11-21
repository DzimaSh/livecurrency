package com.example.exception;

public class MaximumUsersReachedException extends Exception {
    public MaximumUsersReachedException(String message) {
        super(message);
    }
}
