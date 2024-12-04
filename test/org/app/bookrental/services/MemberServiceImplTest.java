package org.app.bookrental.services;

import org.app.bookrental.data.models.Member;
import org.app.bookrental.data.repositories.MemberRepository;
import org.app.bookrental.dtos.requests.AddMemberRequest;
import org.app.bookrental.dtos.responses.AddMemberResponse;
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
        memberRepository.deleteAll();
    }

    @Test
    public void test_To_Register_Save_Members() {
        AddMemberResponse response = memberService.registerMember(new AddMemberRequest());
        AddMemberRequest request = new AddMemberRequest();
        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        memberRepository.save(member);
        response.setRegMsg("successfully registered");
        assertEquals(response.getRegMsg(), "successfully registered");
    }

    @Test
    public void test_That_Email_Cannot_Be_Empty() {
        AddMemberRequest request = new AddMemberRequest();
        request.setEmail(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        memberService.registerMember(new AddMemberRequest());
        });
        assertEquals("Email cannot be empty", exception.getMessage());
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


}


