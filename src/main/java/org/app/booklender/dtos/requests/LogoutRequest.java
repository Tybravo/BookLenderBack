package org.app.booklender.dtos.requests;

import lombok.Data;

@Data
public class LogoutRequest {
    private String id;
    private String email;
    private String password;
    private Boolean sessionStatus = false;
}
