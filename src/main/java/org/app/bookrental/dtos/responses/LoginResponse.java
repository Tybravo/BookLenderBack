package org.app.bookrental.dtos.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String token;

    private String regMsg = "Registration successful";

}

