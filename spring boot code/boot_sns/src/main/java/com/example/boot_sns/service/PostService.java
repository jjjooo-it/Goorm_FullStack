package com.example.boot_sns.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.boot_sns.model.Post;
import com.example.boot_sns.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}
}