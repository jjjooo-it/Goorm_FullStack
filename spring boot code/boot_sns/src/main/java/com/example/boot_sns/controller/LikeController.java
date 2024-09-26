package com.example.boot_sns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boot_sns.model.Like;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.service.LikeService;
import com.example.boot_sns.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {
	private final LikeService likeService;
	private final PostService postService;

	@PostMapping("/toggle/{postId}")
	public String toggleLike(@PathVariable Long postId,
			HttpSession session) {
		Member loginMember = 
				(Member) session.getAttribute("loginMember");
		if (loginMember != null) {
			Like oldLike = likeService.findByPostIdAndMemberId(
					postId, loginMember.getId()).orElse(null);
			if (oldLike == null) {				
				Like like = new Like();
				like.setMember(loginMember);
				like.setPost(postService.findById(postId).get());
				likeService.save(like);
			} else {
				likeService.deleteById(oldLike.getId());
			}
		}
		return "redirect:/posts/" + postId;
	}
}
