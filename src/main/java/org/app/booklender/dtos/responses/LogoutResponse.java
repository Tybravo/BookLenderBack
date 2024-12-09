package org.app.booklender.dtos.responses;

import lombok.Data;

@Data
public class LogoutResponse {
    private String id;
    private String token;

    private String LogoutMsg = "Logged Out successfully";
}
