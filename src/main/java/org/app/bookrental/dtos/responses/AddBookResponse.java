package org.app.bookrental.dtos.responses;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AddBookResponse {
    private String id;
    private String title;
    private String author;
    private String description;
    private boolean isAvail;
    private LocalDate rentalDate;
}
