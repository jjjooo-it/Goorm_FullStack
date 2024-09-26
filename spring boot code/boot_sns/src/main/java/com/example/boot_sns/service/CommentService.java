package com.example.boot_sns.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.boot_sns.model.Comment;
import com.example.boot_sns.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}

	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}
}