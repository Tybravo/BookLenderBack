package org.app.bookrental.dtos.requests;

import lombok.Data;

@Data
public class AddMemberRequest {

    private String fullName;
    private String email = "twinebravo@gmail.com";
    private String password;
    private String phoneNumber;
    private String address;

    private String testemail;
}

