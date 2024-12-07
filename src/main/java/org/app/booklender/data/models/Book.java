package org.app.booklender.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private boolean isAvail;
    private LocalDateTime creationDate = LocalDateTime.now();
}
