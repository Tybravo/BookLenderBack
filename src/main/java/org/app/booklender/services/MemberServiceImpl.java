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
    MemberRepository memberRepository;

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
        addMemberRequest.getPassword().length() > 8 ) {
            throw new IllegalArgumentException("Registration Fields cannot be empty");
        }
        if(!addMemberRequest.getEmail().contains("@") || !addMemberRequest.getEmail().contains(".") || !addMemberRequest.getEmail().contains("com")) {
            throw new IllegalArgumentException("You forgot to include a character in Email");
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
            regResponse.setFullName(addMemberRequest.getFullName());
            regResponse.setEmail(addMemberRequest.getEmail());
            regResponse.setPhoneNumber(addMemberRequest.getPhoneNumber());
            regResponse.setAddress(addMemberRequest.getAddress());
            regResponse.setCreationDate(addMemberRequest.getCreationDate());
            regResponse.setSessionStatus(addMemberRequest.isSessionStatus());
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
            regResponse.setEmail(foundMemberEmail.getEmail());
            regResponse.setSessionStatus(foundMemberEmail.isSessionStatus());
            regResponse.setLogMsg("Email Login successful");
          return regResponse;
        } else{
            throw new IllegalArgumentException("Cannot find email");
        }
    }

    @Override
    public LoginResponse loginPassword(LoginRequest loginRequest) {
            Member foundMemberPassword = findMemberByEmail(loginRequest.getEmail());
            if (foundMemberPassword != null && (foundMemberPassword.getPassword().equals(loginRequest.getPassword()))) {
                foundMemberPassword.setSessionStatus(true);
                memberRepository.save(foundMemberPassword);
                LoginResponse regResponse = new LoginResponse();
                regResponse.setId(foundMemberPassword.getId());
                regResponse.setEmail(foundMemberPassword.getEmail());
                regResponse.setSessionStatus(foundMemberPassword.isSessionStatus());
                regResponse.setLogMsg("Correct password! Member Login successful");
                 return regResponse;
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            } else {
                throw new IllegalArgumentException("You have entered a wrong password or missing email");
            }
    }

    @Override
    public Member alreadyInSession(LoginRequest loginRequest) {
        Member getMemberStatus = findMemberByEmail(loginRequest.getEmail());
        if(getMemberStatus != null && !getMemberStatus.isSessionStatus()){
            throw new IllegalArgumentException("Not in session! You accidentally miss your way");
        }
        if (getMemberStatus != null) {
            if (loginRequest.getEmail().equals(getMemberStatus.getEmail()) &&
                    loginRequest.getPassword().equals(getMemberStatus.getPassword()) ) {
                getMemberStatus.setSessionStatus(true);
                memberRepository.save(getMemberStatus);
                getMemberStatus.setStatusMsg("Already in session");
            }
        }
            return getMemberStatus;
    }


    @Override
    public Member loginMember(LoginRequest loginRequest) {
        Member foundMember = findMemberByEmail(loginRequest.getEmail());

        if(loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Email or password cannot be empty");
        }
        if (foundMember != null && foundMember.getEmail().equals(loginRequest.getEmail()) &&
                loginRequest.getPassword().equals(foundMember.getPassword())) {
            foundMember.setSessionStatus(true);
            memberRepository.save(foundMember);
            foundMember.setId(foundMember.getId());
            foundMember.setLogMsg("Member Login successful");
            return foundMember;
        } else {
            throw new IllegalArgumentException("Wrong email or password entered");
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

}

