package com.example.boot_sns.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boot_sns.model.Member;
import com.example.boot_sns.model.Post;
import com.example.boot_sns.service.LikeService;
import com.example.boot_sns.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;
	private final LikeService likeService;
	
	@GetMapping("/create")
	public String postForm(Model model) {
		model.addAttribute("post", new Post());
		return "post/create";
	}
	
	@PostMapping("/create")
	public String createPost(@ModelAttribute Post post,
			HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember != null) {
			post.setAuthor(loginMember);
			postService.save(post);
		}
		return "redirect:/posts";
	}
	
	@GetMapping
	public String postList(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "post/list";
	}
	
	@GetMapping("/{id}")
	public String postDetail(@PathVariable Long id,
			Model model,
			HttpSession session) {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty()) {
			return "redirect:/posts";
		}
		model.addAttribute("post", post.get());
		
		Member loginMember = (Member) session
					.getAttribute("loginMember");
		if (loginMember != null) {			
			Boolean isLiked = likeService.findByPostIdAndMemberId(
					id, loginMember.getId()).isPresent();
			model.addAttribute("isLiked", isLiked);
		}
		return "post/view";
	}
}
