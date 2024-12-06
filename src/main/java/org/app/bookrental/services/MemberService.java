package org.app.bookrental.services;

import org.app.bookrental.data.models.Member;
import org.app.bookrental.dtos.requests.LoginRequest;
import org.app.bookrental.dtos.requests.AddMemberRequest;
import org.app.bookrental.dtos.responses.AddMemberResponse;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member findMemberByEmail(String emailAddy);

    AddMemberResponse registerMember(AddMemberRequest addMemberRequest);

    void emailAlreadyExists(AddMemberRequest addMemberRequest);

    void emailCannotBeEmpty(AddMemberRequest addMemberRequest);

    void loginEmail(LoginRequest loginRequest);

    void loginPassword(LoginRequest loginRequest);

    Member loginMember(LoginRequest loginRequest);


}