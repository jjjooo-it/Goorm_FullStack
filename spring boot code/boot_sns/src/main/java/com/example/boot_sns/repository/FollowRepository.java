package com.example.boot_sns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boot_sns.model.Follow;

@Repository
public interface FollowRepository
	extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
