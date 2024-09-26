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

import com.example.boot_sns.model.Comment;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.model.Post;
import com.example.boot_sns.service.CommentService;
import com.example.boot_sns.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;
	private final PostService postService;
	
	@GetMapping("/post/{postId}")
	public String commentList(@PathVariable Long postId,
			Model model) {
		Optional<Post> post = postService.findById(postId);
		if (post.isEmpty()) {
			return "redirect:/posts";
		}
		model.addAttribute("comments", post.get().getComments());
		model.addAttribute("postId", postId);
		return "comment/list";
	}
	
	@PostMapping("/create")
	public String createComment(@ModelAttribute Comment comment,
			@RequestParam Long postId,
			HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember != null) {
			comment.setMember(loginMember);
			Optional<Post> post = postService.findById(postId);
			if (post.isEmpty()) {
				return "redirect:/posts";
			}
			comment.setPost(post.get());
			commentService.save(comment);
		}
		return "redirect:/comments/post/" + postId;
	}
	
	@GetMapping("delete/{id}")
	public String deleteComment(@PathVariable Long id,
			HttpSession session) {
		Optional<Comment> comment = commentService.findById(id);
		if (comment.isEmpty()) {
			return "redirect:/posts";
		}
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember != null 
				&& loginMember.getId()
					== comment.get().getMember().getId()) {
			commentService.deleteById(id);
		}
		return "redirect:/comments/post/" + comment.get().getPost().getId();
	}
}
