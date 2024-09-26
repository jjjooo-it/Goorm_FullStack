package com.example.boot_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.boot_security.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity // Spring Security 관련된 설정을 활성화
@EnableMethodSecurity // 메서드 수준 보안
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			// 요청(브라우저...)에 대해서 어떤 권한을 요구할 것인가
			.authorizeHttpRequests(
				(requests) -> requests
				.requestMatchers("/", "/join").permitAll() // 모든 사용자에 대해 허용
				.requestMatchers("/premium/**").hasRole("premium")
				.requestMatchers("/user/**").hasAnyRole("premium", "user")
				.anyRequest().authenticated() // 인증이 필요하게 만들겠다
			) // 모든 접근 요청에 대해서 로그인 필요 여부를 결정
			.formLogin(
				(form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/main")
				.permitAll() // /login
			) // /login
			// oauth2login
			.oauth2Login(oauth2login -> oauth2login
					.loginPage("/login")
					.userInfoEndpoint(userInfoEndpoint
						-> userInfoEndpoint.userService(
								customOAuth2UserService))
					.defaultSuccessUrl("/main")
					.permitAll()
			)
			.logout(
				(logout) -> logout.permitAll()
			); // /logout
		return http.build(); // 필터 적용
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		// Spring Security 써봤나? -> Config 해봤나?
		// passwordEncoder? -> BCryptPasswordEncoder...
		return new BCryptPasswordEncoder();
	}
}
