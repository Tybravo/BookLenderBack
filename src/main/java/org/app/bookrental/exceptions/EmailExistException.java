package org.app.bookrental.exceptions;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);

    }
}

