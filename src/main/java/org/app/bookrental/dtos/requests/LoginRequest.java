package org.app.bookrental.dtos.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String email;
    private String password;
}
