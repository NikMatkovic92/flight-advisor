package com.htec.config.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikola Matkovic
 */

@Component
@Slf4j
public class JwtUtils {

	private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	@Value("${flightAdvisor.app.tokenDuration}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		var userPrincipal = (UserDetails) authentication.getPrincipal();

		return Jwts.builder()
			.setSubject((userPrincipal.getUsername()))
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
			.signWith(key)
			.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
