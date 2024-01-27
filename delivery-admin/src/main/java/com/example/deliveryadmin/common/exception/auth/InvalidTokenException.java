package com.example.deliveryadmin.common.exception.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
