package com.ctf.status;

public class BadStatusException extends RuntimeException {

    public BadStatusException(String message) {
        super(message);
    }

    public BadStatusException(String message, Throwable cause) {
        super(message, cause);
    }

}