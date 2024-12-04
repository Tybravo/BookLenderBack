package org.app.bookrental.dtos.responses;

import lombok.Data;
import java.time.LocalDate;

@Data
public class   AddMemberResponse {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate createdAt;

    private String regMsg ;
}
