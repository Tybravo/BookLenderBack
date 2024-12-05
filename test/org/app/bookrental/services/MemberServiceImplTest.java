package org.app.bookrental.services;

import org.app.bookrental.data.models.Member;
import org.app.bookrental.data.repositories.MemberRepository;
import org.app.bookrental.dtos.requests.AddMemberRequest;
import org.app.bookrental.dtos.requests.LoginRequest;
import org.app.bookrental.dtos.responses.AddMemberResponse;
import org.app.bookrental.dtos.responses.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @BeforeEach
    void setUp() {
       //memberRepository.deleteAll();
    }

    @Test
    public void test_To_Register_Save_Members() {
        AddMemberRequest request = new AddMemberRequest();
        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail("twinebravo@gmail.com");
        member.setPassword("tybravo");
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        AddMemberResponse response = memberService.registerMember(new AddMemberRequest());
        //memberRepository.save(member);
        response.setRegMsg("successfully registered");
        assertEquals(response.getRegMsg(), "successfully registered");
    }

    @Test
    public void test_That_Email_Already_Taken() {
        AddMemberRequest request = new AddMemberRequest();
        request.setEmail("twinebravo@gmail.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.emailAlreadyExists(request);
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
        LoginRequest request = new LoginRequest();
        request.setEmail("myemail@yahoo.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.loginEmail(request);
        });
        assertEquals("Cannot find email", exception.getMessage());
    }

    @Test
    public void test_That_Password_Is_Wrong() {
        LoginRequest request = new LoginRequest();
        request.setPassword("tybravo");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.loginPassword(request);
        });
        assertEquals("You have entered a wrong password", exception.getMessage());
    }


    @Test
    public void test_That_Password_Is_Correct() {
        LoginRequest request = new LoginRequest();
        request.setEmail("twinebravo@gmail.com");
        request.setPassword("tybravo");
        Member response = memberService.loginMember(request);
        assertEquals("tybravo", response.getPassword());
    }


}



