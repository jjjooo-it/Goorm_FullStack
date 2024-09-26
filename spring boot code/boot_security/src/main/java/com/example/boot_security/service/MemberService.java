package com.example.boot_security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.boot_security.model.Member;
import com.example.boot_security.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	// bean 등록된 걸 의존성 주입으로 가져다 쓰겠다.
	
	public void save(Member member) {
		// member.getPassword() -> 우리가 가입할 때 form으로 입력한 패스워드
		member.setRole("USER");
		// passwordEncoder.encode(member.getPassword())
		// -> bcrypt로 변환된 비밀번호
		member.setPassword(passwordEncoder.encode(
				member.getPassword()));
		// db 자체에는 우리가 원래 입력하려고 했던 비밀번호가 저장되는게 아니라
		// bcrypt로 변환된(해싱된) 암호화된 비밀번호가 저장되게 되고,
		// 이건 CustomUserDetailsService에서는 등록된 passwordEncoder로
		// 알아서 해석해서 사용하게 됨.
		memberRepository.save(member);
	}

	public void changeRole(String username, String role) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("유저 없음"));
		member.setRole(role);
		memberRepository.save(member);
	}
	
	@PreAuthorize("hasRole('premium')")
	public void forPremium1() {
		
	}
	
	@Secured("premium")
	public void forPremium2() {
		
	}
	
	@PreAuthorize("hasAnyRole('premium', 'user')")
	public void forPremiumOrUser() {
		
	}
}
