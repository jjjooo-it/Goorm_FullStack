package com.example.boot_sns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boot_sns.model.Follow;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.service.FollowService;
import com.example.boot_sns.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
	private final FollowService followService;
	private final MemberService memberService;

	@PostMapping("/toggle/{followingId}")
	public String toggleLike(@PathVariable Long followingId, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember != null && loginMember.getId() != followingId) {
			Follow oldFollow = followService.findByFollowerIdAndFollowingId(loginMember.getId(), followingId)
					.orElse(null);
			if (oldFollow == null) {
				Follow follow = new Follow();
				follow.setFollowing(memberService.findById(followingId).get());
				follow.setFollower(loginMember);
				followService.save(follow);
			} else {
				followService.deleteById(oldFollow.getId());
			}
		}
		return "redirect:/members/" + followingId;
	}
}
