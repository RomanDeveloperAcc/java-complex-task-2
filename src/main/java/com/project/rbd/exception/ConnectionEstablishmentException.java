package com.project.rbd.exception;

public class ConnectionEstablishmentException extends RuntimeException {
    public ConnectionEstablishmentException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
