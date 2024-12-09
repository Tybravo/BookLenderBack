package org.app.booklender.controllers;

import org.app.booklender.dtos.requests.AddMemberRequest;
import org.app.booklender.dtos.requests.LoginRequest;
import org.app.booklender.dtos.responses.AddMemberResponse;
import org.app.booklender.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AddMemberRequest addMemberRequest) {
        try {
            AddMemberResponse response = memberService.registerMember(addMemberRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            memberService.loginEmail(loginRequest);
            memberService.loginPassword(loginRequest);
            return ResponseEntity.ok(memberService.loginMember(loginRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Implement logout logic if needed
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMember(@RequestParam String email) {
        try {
            return ResponseEntity.ok(memberService.findMemberByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }
}
