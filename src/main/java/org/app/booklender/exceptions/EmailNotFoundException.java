package org.app.booklender.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String emailNotFound) {
        super(emailNotFound);
    }
}
