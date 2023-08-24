package com.sahu.util;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sahu.service.dto.CustomUserDetailsDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt-secret}")
	private String jwtSecretKey;

	@Value("${app-jwt-expiration-milliseconds}")
	private Long jwtExpireationTime;

	public String generateAccessToken(CustomUserDetailsDTO CustomUserDetailsDTO) {
		Date expireDate = new Date(new Date().getTime() + jwtExpireationTime);
		String token = Jwts.builder().setSubject(CustomUserDetailsDTO.getUsername()).setIssuedAt(new Date())
				.setExpiration(expireDate).signWith(key()).compact();
		logger.debug("Token - "+token);
		return token;
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String getUsername(String token) {
		return getJWTClaims(token).getSubject();
	}

	
	public Date getExpirationDate(String token) {
		return getJWTClaims(token).getExpiration();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}

	private Claims getJWTClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
	}
}
