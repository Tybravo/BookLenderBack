package org.app.booklender.dtos.responses;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class   AddMemberResponse {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean sessionStatus = false;
    private LocalDateTime creationDate = LocalDateTime.now();
    private String regMsg ;
}
