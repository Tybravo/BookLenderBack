package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
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
    public void emailAlreadyExists(AddMemberRequest addMemberRequest) {
        Member getMember = findMemberByEmail(addMemberRequest.getEmail());
        if (getMember != null && getMember.getEmail().equals(addMemberRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }
    }


    @Override
    public void emailCannotBeEmpty(AddMemberRequest addMemberRequest) {
        //Member getMember = findMemberByEmail(addMemberRequest.getEmail());
        if (addMemberRequest.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
    }


    @Override
    public AddMemberResponse registerMember(AddMemberRequest addMemberRequest) {
        Member getMember = findMemberByEmail(addMemberRequest.getEmail());
        if(getMember == null) {
            Member member = new Member();
            member.setFullName(addMemberRequest.getFullName());
            member.setEmail(addMemberRequest.getEmail());
            member.setPassword(addMemberRequest.getPassword());
            member.setPhoneNumber(addMemberRequest.getPhoneNumber());
            member.setAddress(addMemberRequest.getAddress());
            //memberRepository.save(member);
        }
        AddMemberResponse regResponse = new AddMemberResponse();
        regResponse.setRegMsg("Registration successful");
        return regResponse;
    }

    @Override
    public void loginEmail(LoginRequest loginRequest) {
        Member foundMember = findMemberByEmail(loginRequest.getEmail());
        if (foundMember == null) {
            throw new IllegalArgumentException("Cannot find email");
        }
    }

    @Override
    public void loginPassword(LoginRequest loginRequest) {
        Member memberPassword = findMemberByEmail(loginRequest.getEmail());
        if (memberPassword == null || ! memberPassword.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("You have entered a wrong password");
        }
    }

    @Override
    public Member loginMember(LoginRequest loginRequest) {
        Member foundMember = findMemberByEmail(loginRequest.getEmail());
        if (foundMember != null && foundMember.getPassword().equals(loginRequest.getPassword())) {
            return foundMember;
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }




}