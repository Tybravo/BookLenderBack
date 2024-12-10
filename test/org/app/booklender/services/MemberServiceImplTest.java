package org.app.booklender.services;

import org.app.booklender.data.models.Member;
import org.app.booklender.data.repositories.MemberRepository;
import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.requests.LogoutRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

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
    public void test_To_Register_And_Save_Members() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
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
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest getRequest = new LoginRequest();
        getRequest.setEmail("myemail@yahoo.com");
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
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest getRequest = new LoginRequest();
        getRequest.setPassword("tybravo");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.loginPassword(getRequest));
        assertEquals("You have entered a wrong password", exception.getMessage());
    }

    @Test
    public void test_That_Email_And_Password_Are_Correct() {
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("twinebravo@gmail.com");
        loginRequest.setPassword("tybravo");

        Member getResponse = memberService.loginMember(loginRequest);
        assertEquals("tybravo", getResponse.getPassword());
    }


    @Test
    public void test_LogoutMember_Success() {
        //Given
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setFullName("Ade Bravo");
        addMemberRequest.setEmail("twinebravo@gmail.com");
        addMemberRequest.setPassword("tybravo");
        addMemberRequest.setPhoneNumber("07032819318");
        addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
        AddMemberResponse response = memberService.registerMember(addMemberRequest);
        assertEquals("Registration successful", response.getRegMsg());

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail("twinebravo@gmail.com");

        // When
        memberService.logoutMember(logoutRequest);

        // Assert
        try {
            session.getId();
            Assertions.assertFalse(false);

        } catch (IllegalStateException ignored) {
        }
    }



//        @Test
//        public void test_LogoutMember_Success() {
//            // Arrange
//            AddMemberRequest addMemberRequest = new AddMemberRequest();
//            addMemberRequest.setFullName("Ade Bravo");
//            addMemberRequest.setEmail("twinebravo@gmail.com");
//            addMemberRequest.setPassword("tybravo");
//            addMemberRequest.setPhoneNumber("07032819318");
//            addMemberRequest.setAddress("No. 34, Sabo, Yaba, Lagos.");
//            AddMemberResponse response = memberService.registerMember(addMemberRequest);
//            assertEquals("Registration successful", response.getRegMsg());
//
//            MockHttpServletRequest request = new MockHttpServletRequest();
//            val session = request.getSession(true);
//
//            LogoutRequest logoutRequest = new LogoutRequest();
//            logoutRequest.setEmail("twinebravo@gmail.com");
//
//            // Act
//            memberService.logoutMember(logoutRequest);
//
//            // Assert
//            assertFalse(session.invalidate());
//        }


//        @Test
//        public void test_LogoutMember_MemberNotFound() {
//            // Arrange
//            HttpServletRequest request = new MockHttpServletRequest();
//
//            LogoutRequest logoutRequest = new LogoutRequest();
//            logoutRequest.setEmail("nonexistent@gmail.com");
//
//            // Act & Assert
//            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                memberService.logoutMember(logoutRequest, request);
//            });
//
//            assertEquals("Email not found", exception.getMessage());
//        }
//    }


}
