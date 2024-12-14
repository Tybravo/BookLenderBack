package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.requests.LogoutRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.app.booklender.dtos.responses.LoginResponse;
import org.app.booklender.dtos.responses.LogoutResponse;
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
    void eraseAll() {
        memberRepository.deleteAll();
    }


    @Test
    public void test_To_Register_And_Save_Members() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());
    }

    @Test
    public void test_That_Email_Already_Taken() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.emailAlreadyExists(addMemberRequest));
        assertEquals("Email is already taken", exception.getMessage());
    }

    @Test
    public void test_That_Email_Cannot_Be_Empty() {
        AddMemberRequest request = new AddMemberRequest();
        request.setEmail(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.emailCannotBeEmpty(new AddMemberRequest()));
        assertEquals("Registration Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void test_That_User_Cannot_Login_With_Wrong_Email() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest getRequest = new LoginRequest();
        getRequest.setEmail("myemail@yahoo.com");
        getRequest.setSessionStatus(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.loginEmail(getRequest));
        assertEquals("Cannot find email", exception.getMessage());
    }

    @Test
    public void test_That_User_Cannot_Login_With_Wrong_Password() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest getRequest = new LoginRequest();
        getRequest.setPassword("bravo");
        getRequest.setSessionStatus(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.loginPassword(getRequest));
        assertEquals("You have entered a wrong password", exception.getMessage());
    }

    @Test
    public void test_That_User_can_Login_With_Right_Email() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");

        LoginResponse getResponse = memberService.loginEmail(loginRequest);
        assertEquals("Email Login successful", getResponse.getRegMsg());
    }

    @Test
    public void test_That_User_can_Login_With_Right_Password() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");

        LoginResponse getResponse = memberService.loginPassword(loginRequest);
        assertEquals("Correct password! Member Login successful", getResponse.getRegMsg());
    }

    @Test
    public void test_That_Email_And_Password_Are_Correct() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");

        Member getResponse = memberService.loginMember(loginRequest);
        assertEquals("twinebravo@gmail.com", getResponse.getEmail());
        assertEquals("tybravo", getResponse.getPassword());
        LoginResponse getResponse2= new LoginResponse();
        getResponse2.setRegMsg("Member Login successful");
        assertEquals("Member Login successful", getResponse2.getRegMsg());

    }

    @Test
    public void test_That_Login_Session_Is_Validated() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setSessionStatus(true);

        Member getResponse = memberService.loginSession(loginRequest);
        assertEquals("twinebravo@gmail.com", getResponse.getEmail());
        assertTrue(getResponse.isSessionStatus(), String.valueOf(true));
    }

    @Test
    public void test_That_Login_Session_Is_Currently_Running_And_Cannot_Login_Again() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");
        loginRequest.setSessionStatus(true);

        Member getResponse = memberService.loginMember(loginRequest);
        assertEquals("twinebravo@gmail.com", getResponse.getEmail());
        assertEquals("tybravo", getResponse.getPassword());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.loginSession(loginRequest));
        assertEquals("You are already in session", exception.getMessage());
    }

    @Test
    public void test_That_Login_Session_With_Email_And_Password_Are_Valid() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");
        loginRequest.setSessionStatus(true);

        Member getResponse = memberService.loginMember(loginRequest);
        assertEquals("twinebravo@gmail.com", getResponse.getEmail());
        assertEquals("tybravo", getResponse.getPassword());
        assertTrue(getResponse.isSessionStatus(), String.valueOf(true));
    }

    @Test
    public void test_That_Logout_Session_Is_Invalidated() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        addMemberRequest.setSessionStatus(false);
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");
        loginRequest.setSessionStatus(true);
        memberService.loginMember(loginRequest);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail("twinebravo@gmail.com");
        logoutRequest.setSessionStatus(false);

        LogoutResponse getResponse = memberService.logoutMember(logoutRequest);
        assertEquals("twinebravo@gmail.com", getResponse.getEmail());
        assertEquals("Logged out successfully", getResponse.getLogoutMsg());
    }

}
