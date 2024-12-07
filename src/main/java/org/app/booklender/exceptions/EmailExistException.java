package org.app.booklender.exceptions;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);

    }
}

