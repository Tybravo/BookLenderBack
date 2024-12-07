package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @BeforeEach
    void setUp() {
       memberRepository.deleteAll();

    }

    @Test
    public void test_To_Register_Save_Members() {
       Member member = new Member();
        member.setFullName("Ade Bravo");
        member.setEmail("twinebravo@gmail.com");
        member.setPassword("tybravo");
        member.setPhoneNumber("07032819318");
        AddMemberResponse response = memberService.registerMember(new AddMemberRequest());
        memberRepository.save(member);
        response.setRegMsg("successfully registered");
        assertEquals(response.getRegMsg(), "successfully registered");
    }

    @Test
    public void test_That_Email_Already_Taken() {
        AddMemberRequest request = new AddMemberRequest();
        request.setFullName("Adetayo Bravo");
        request.setEmail("twinebravo@gmail.com");
        request.setPassword("tybravo");
        request.setPhoneNumber("08033664670");
        AddMemberResponse response = memberService.registerMember(request);
        assertEquals("Registration successful", response.getRegMsg());

        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        memberRepository.save(member);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {memberService.emailAlreadyExists(request);
        });
        assertEquals("Email is already taken", exception.getMessage());
    }

    @Test
    public void test_That_Email_Cannot_Be_Empty() {
        AddMemberRequest request = new AddMemberRequest();
        request.setEmail(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.emailCannotBeEmpty(new AddMemberRequest());
        });
        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    public void test_That_User_Cannot_Login_With_Wrong_Email() {
        AddMemberRequest request = new AddMemberRequest();
        request.setFullName("Adetayo Bravo");
        request.setEmail("twinebravo@gmail.com");
        request.setPassword("tybravo");
        request.setPhoneNumber("08033664670");
        AddMemberResponse response = memberService.registerMember(request);
        assertEquals("Registration successful", response.getRegMsg());

        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        memberRepository.save(member);

        LoginRequest getRequest = new LoginRequest();
        getRequest.setEmail("myemail@yahoo.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.loginEmail(getRequest);
        });
        assertEquals("Cannot find email", exception.getMessage());
    }

    @Test
    public void test_That_Password_Is_Wrong() {
        AddMemberRequest request = new AddMemberRequest();
        request.setFullName("Adetayo Bravo");
        request.setEmail("twinebravo@gmail.com");
        request.setPassword("tybravo");
        request.setPhoneNumber("08033664670");
        AddMemberResponse response = memberService.registerMember(request);
        assertEquals("Registration successful", response.getRegMsg());

        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        memberRepository.save(member);

        LoginRequest getRequest = new LoginRequest();
        getRequest.setPassword("tybravo");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.loginPassword(getRequest);
        });
        assertEquals("You have entered a wrong password", exception.getMessage());
    }

    @Test
    public void test_That_Password_Is_Correct() {
        AddMemberRequest request = new AddMemberRequest();
        request.setFullName("Adetayo Bravo");
        request.setEmail("twinebravo@gmail.com");
        request.setPassword("tybravo");
        request.setPhoneNumber("08033664670");
        AddMemberResponse response = memberService.registerMember(request);
        assertEquals("Registration successful", response.getRegMsg());

        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setPhoneNumber(request.getPhoneNumber());
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");

        Member getResponse = memberService.loginMember(loginRequest);
        assertEquals("tybravo", getResponse.getPassword());
    }

}



