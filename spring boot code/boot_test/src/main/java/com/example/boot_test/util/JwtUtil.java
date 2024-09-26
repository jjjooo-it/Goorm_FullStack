package com.example.boot_test.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String SECRET_KEY
		= "yourSecretKeyHereMustBeAtLeast32BytesLong";
	private final Key key
		= Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	// 토큰 생성
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(
						System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// 토큰 -> Claim
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	// Username 추출
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}
}
