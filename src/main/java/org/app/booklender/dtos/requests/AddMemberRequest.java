package org.app.booklender.dtos.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AddMemberRequest {

    private String id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String statusMsg;
    private String regMsg;
    private boolean sessionStatus = false;
    private LocalDateTime creationDate = LocalDateTime.now();
}

