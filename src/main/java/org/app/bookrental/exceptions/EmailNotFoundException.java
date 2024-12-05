package org.app.bookrental.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String emailNotFound) {
        super(emailNotFound);
    }
}
