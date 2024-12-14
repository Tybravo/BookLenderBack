package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LogoutRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.app.booklender.dtos.responses.LoginResponse;
import org.app.booklender.dtos.responses.LogoutResponse;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMemberByEmail(String emailAddy);

    AddMemberResponse registerMember(AddMemberRequest addMemberRequest);

    void emailAlreadyExists(AddMemberRequest addMemberRequest);

    void emailCannotBeEmpty(AddMemberRequest addMemberRequest);

    Member loginSession(LoginRequest loginRequest);

    LoginResponse loginEmail(LoginRequest loginRequest);

    LoginResponse loginPassword(LoginRequest loginRequest);

    Member loginMember(LoginRequest loginRequest);

    LogoutResponse logoutMember(LogoutRequest logoutRequest);

}