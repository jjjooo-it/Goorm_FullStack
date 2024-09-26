package com.example.boot_sns.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.boot_sns.model.Like;
import com.example.boot_sns.model.Post;
import com.example.boot_sns.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;

	public Like save(Like like) {
		return likeRepository.save(like);
	}

	public Optional<Like> findByPostIdAndMemberId(Long postId, Long memberId) {
		return likeRepository.findByPostIdAndMemberId(postId, memberId);
	}

	public void deleteById(Long id) {
		likeRepository.deleteById(id);
	}
}