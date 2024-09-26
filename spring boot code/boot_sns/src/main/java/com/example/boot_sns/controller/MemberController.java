package com.example.boot_sns.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boot_sns.model.Member;
import com.example.boot_sns.service.FollowService;
import com.example.boot_sns.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;
	private final FollowService followService;
	
	@GetMapping("/register")
	public String registerMember(Model model) {
		model.addAttribute("member", new Member());
		return "member/register";
	}
	
	@PostMapping("/register")
	public String addMember(@ModelAttribute Member member) {
		memberService.save(member);
		return "redirect:/members/login";
	}
	
	@GetMapping
	public String memberList(Model model) {
		model.addAttribute("members", memberService.findAll());
		return "member/list";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username,
			@RequestParam String password,
			HttpSession session) {
		Member member = memberService.login(username, password);
		if (member == null) {
			return "redirect:/members/login";
		}
		session.setAttribute("loginMember", member);
		return "redirect:/members";
	}
	
	@GetMapping("/{id}")
	public String memberDetail(@PathVariable Long id,
			Model model, HttpSession session) {
		Optional<Member> member = memberService.findById(id);
		
		if (member.isEmpty()) {
			return "redirect:/members";
		}
		model.addAttribute("member", member.get());
		model.addAttribute("posts", member.get().getPosts());
		
		Member loginMember = 
				(Member) session.getAttribute("loginMember");
		if (loginMember != null) {			
			Boolean isFollowing = followService
					.findByFollowerIdAndFollowingId(loginMember.getId(),
							id).isPresent();
			model.addAttribute("isFollowing", isFollowing);
		}
		return "member/view";
	}
}
