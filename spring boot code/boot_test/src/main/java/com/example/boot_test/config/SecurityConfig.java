package com.example.boot_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.boot_test.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter filter;
	
	@Bean
	SecurityFilterChain securityFilterChain(
			HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((authz) -> authz
			.requestMatchers("/api/public/**")
					.permitAll()
			.requestMatchers("/api/protected/**")
					.authenticated())
			.addFilterBefore(filter,
				UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
