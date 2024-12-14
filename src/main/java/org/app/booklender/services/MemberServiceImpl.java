package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.requests.LogoutRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.app.booklender.dtos.responses.LoginResponse;
import org.app.booklender.dtos.responses.LogoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Member loginSession(LoginRequest loginRequest) {
        Member getMemberStatus = findMemberByEmail(loginRequest.getEmail());
        if (getMemberStatus != null) {
            if (getMemberStatus.isSessionStatus()) {
                throw new IllegalArgumentException("You are already in session");
            } else {
                getMemberStatus.setSessionStatus(true);
                memberRepository.save(getMemberStatus);
            }
        }
        return getMemberStatus;
    }

    @Override
    public LoginResponse loginEmail(LoginRequest loginRequest) {
        //loginSession(loginRequest);
        Member foundMemberEmail = findMemberByEmail(loginRequest.getEmail());
      if(foundMemberEmail != null && (foundMemberEmail.getEmail().equals(loginRequest.getEmail()) )){
          //foundMemberEmail.setSessionStatus(true);
          LoginResponse regResponse = new LoginResponse();
            regResponse.setId(foundMemberEmail.getId());
            regResponse.setSessionStatus(foundMemberEmail.isSessionStatus());
            regResponse.setRegMsg("Email Login successful");
          return regResponse;
        } else{
            throw new IllegalArgumentException("Cannot find email");
        }
    }

    @Override
    public LoginResponse loginPassword(LoginRequest loginRequest) {
        Member foundMemberPassword = findMemberByEmail(loginRequest.getEmail());
        if(foundMemberPassword != null && (foundMemberPassword.getPassword().equals(loginRequest.getPassword()) )){
            loginSession(loginRequest);
            foundMemberPassword.setSessionStatus(true);
            LoginResponse regResponse = new LoginResponse();
            regResponse.setId(foundMemberPassword.getId());
            regResponse.setRegMsg("Correct password! Member Login successful");
            return regResponse;
            }
        if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        } else{
            throw new IllegalArgumentException("You have entered a wrong password");
        }
    }

    @Override
    public Member loginMember(LoginRequest loginRequest) {
        Member foundMember = findMemberByEmail(loginRequest.getEmail());

        if (foundMember != null && foundMember.getPassword().equals(loginRequest.getPassword())) {
            loginSession(loginRequest);
            foundMember.setSessionStatus(true);
            LoginResponse regResponse = new LoginResponse();
            regResponse.setId(foundMember.getId());
            regResponse.setRegMsg("Member Login successful");
            return foundMember;
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }


    @Override
    public LogoutResponse logoutMember(LogoutRequest logoutRequest) {
        Member foundMember = findMemberByEmail(logoutRequest.getEmail());
        if (foundMember != null) {
           if(!foundMember.isSessionStatus()){
               throw new IllegalArgumentException("You are currently out of session");
           }
           foundMember.setSessionStatus(false);
               memberRepository.save(foundMember);
               LogoutResponse outSession = new LogoutResponse();
               outSession.setEmail(logoutRequest.getEmail());
               outSession.setLogoutMsg("Logged out successfully");
               return outSession;
           } else {
            throw new IllegalArgumentException("Member does not exist");
        }
    }


//    public void invalidateSession(HttpServletRequest request) {
//        HttpSession session = (HttpSession) request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//    }
//
//    private HttpServletRequest getCurrentHttpRequest() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes instanceof ServletRequestAttributes) {
//            return ((ServletRequestAttributes) requestAttributes).getRequest();
//        }
//        throw new IllegalStateException("No current HTTP request found");
//    }

}