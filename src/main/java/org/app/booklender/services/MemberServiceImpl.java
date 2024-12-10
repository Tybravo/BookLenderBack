package org.app.booklender.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.requests.LogoutRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.app.booklender.dtos.responses.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public Member findMemberByEmail(String emailAddy) {
        return memberRepository.findByEmail(emailAddy);
    }

    @Override
    public void emailCannotBeEmpty(AddMemberRequest addMemberRequest) {
        if (addMemberRequest.getEmail() == null || addMemberRequest.getEmail().isEmpty() ||
        addMemberRequest.getPassword() == null || addMemberRequest.getPassword().isEmpty() ||
        addMemberRequest.getFullName() == null || addMemberRequest.getFullName().isEmpty() ||
        addMemberRequest.getPhoneNumber() == null || addMemberRequest.getPhoneNumber().isEmpty() ||
        addMemberRequest.getAddress() == null || addMemberRequest.getAddress().isEmpty() ||
        addMemberRequest.getPassword().length() > 8 || !addMemberRequest.getEmail().contains("@")) {
            throw new IllegalArgumentException("Registration Fields cannot be empty");
        }
    }

    @Override
    public void emailAlreadyExists(AddMemberRequest addMemberRequest) {
        Member getMember = findMemberByEmail(addMemberRequest.getEmail());
        if (getMember != null && getMember.getEmail().equals(addMemberRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }
    }

    @Override
    public AddMemberResponse registerMember(AddMemberRequest addMemberRequest) {
        emailCannotBeEmpty(addMemberRequest);

        Member getMember = findMemberByEmail(addMemberRequest.getEmail());
        AddMemberResponse regResponse = new AddMemberResponse();

        if (getMember == null && addMemberRequest.getEmail() != null) {
            Member member = new Member();
            member.setFullName(addMemberRequest.getFullName());
            member.setEmail(addMemberRequest.getEmail());
            member.setPassword(addMemberRequest.getPassword());
            member.setPhoneNumber(addMemberRequest.getPhoneNumber());
            member.setAddress(addMemberRequest.getAddress());
            memberRepository.save(member);
            regResponse.setRegMsg("Registration successful");
        } else {
            emailAlreadyExists(addMemberRequest);
            regResponse.setRegMsg("Email is already taken");
        }
        return regResponse;
    }

    @Override
    public LoginResponse loginEmail(LoginRequest loginRequest) {
        Member foundMemberEmail = findMemberByEmail(loginRequest.getEmail());
      if(foundMemberEmail != null && (foundMemberEmail.getEmail().equals(loginRequest.getEmail()) )){
            LoginResponse regResponse = new LoginResponse();
            regResponse.setId(foundMemberEmail.getId());
            regResponse.setRegMsg("Email Login successful");
          return regResponse;
        } else{
            throw new IllegalArgumentException("Cannot find email");
        }
    }

    @Override
    public Member loginPassword(LoginRequest loginRequest) {
        Member foundMemberPassword = findMemberByEmail(loginRequest.getEmail());
        if(foundMemberPassword != null && (foundMemberPassword.getPassword().equals(loginRequest.getPassword()) )){
            AddMemberResponse regResponse = new AddMemberResponse();
            regResponse.setRegMsg("Member Login successful");
            return foundMemberPassword;
            } else{
            throw new IllegalArgumentException("You have entered a wrong password");
        }
    }

    @Override
    public Member loginMember(LoginRequest loginRequest) {
        loginPassword(loginRequest);
        Member foundMember = findMemberByEmail(loginRequest.getEmail());
        if (foundMember != null && (foundMember.getPassword().equals(loginRequest.getPassword()) )) {
            AddMemberResponse regResponse = new AddMemberResponse();
            regResponse.setRegMsg("Member Login successful");
            return foundMember;
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    @Override
    public void logoutMember(LogoutRequest logoutRequest) {
        Member foundMember = findMemberByEmail(logoutRequest.getEmail());
        if (foundMember != null) {
            HttpServletRequest request = getCurrentHttpRequest();
            invalidateSession(request);
        } else {
            throw new IllegalArgumentException("Email not found");
        }
    }

    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        throw new IllegalStateException("No current HTTP request found");
    }

}