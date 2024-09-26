package com.example.boot_sns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boot_sns.model.Like;

@Repository
public interface LikeRepository
	extends JpaRepository<Like, Long> {

	Optional<Like> findByPostIdAndMemberId(Long postId, Long memberId);

}
