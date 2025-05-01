package com.example.vadimaprojekts.exceptions;

public class NotValidUsernameException extends RuntimeException {
    public NotValidUsernameException(String message) {
        super(message);
    }
}
