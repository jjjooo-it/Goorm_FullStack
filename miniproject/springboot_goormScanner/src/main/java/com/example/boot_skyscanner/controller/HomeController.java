package com.example.boot_skyscanner.controller;

import com.example.boot_skyscanner.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            boolean isAdmin = new ArrayList<>(authentication.getAuthorities())
                    .get(0).getAuthority().equals("ROLE_ADMIN");
            model.addAttribute("isAdmin", isAdmin);
        }
        return "home";
    }
}
