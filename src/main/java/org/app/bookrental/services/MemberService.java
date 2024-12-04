package org.app.bookrental.services;

import org.app.bookrental.data.models.Member;
import org.app.bookrental.dtos.requests.LoginRequest;
import org.app.bookrental.dtos.responses.LoginResponse;
import org.app.bookrental.dtos.requests.AddMemberRequest;
import org.app.bookrental.dtos.responses.AddMemberResponse;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public interface MemberService {

    Member findMemberByEmail(String emailAddy);

    Member  emailAlreadyExists(AddMemberRequest addMemberRequest);

    AddMemberResponse registerMember(AddMemberRequest addMemberRequest);

    LoginResponse loginMember(LoginRequest loginRequest);

    Member loginUsername(LoginRequest loginRequest);

    Member loginPassword(LoginRequest loginRequest);

}
