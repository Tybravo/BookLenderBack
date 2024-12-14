package org.app.booklender.dtos.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String email;
    private String password;
    private String regMsg;

    private Boolean sessionStatus;
}

