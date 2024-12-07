package org.app.booklender.dtos.requests;

import lombok.Data;

@Data
public class AddMemberRequest {

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    private String testemail;
}

