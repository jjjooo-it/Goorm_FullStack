package com.example.boot_skyscanner.controller;

import com.example.boot_skyscanner.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/join")
    public String joinForm() {return "join";}

    @PostMapping("/join")
    public String join(
                        @RequestParam String email,
                       @RequestParam String password,
                       @RequestParam String enName,
                       @RequestParam(required = false) String koName,
                       @RequestParam(required = false) String gender,
                       @RequestParam(required = false) String mobile,
                       @RequestParam String role,
                        @RequestParam String username) {
        try{
            memberService.join(email, password, enName, koName, gender, mobile, role,username);
            return "redirect:/login";
        } catch (IllegalStateException e) {
            return "redirect:/join?error";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model, Authentication authentication) {
        model.addAttribute("members", memberService.findByUsername(authentication.getName()));
        return "mypage";
    }
}