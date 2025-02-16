package org.app.booklender.dtos.requests;

import lombok.Data;

@Data
public class AddBookRequest {
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    private boolean isAvail;
}
