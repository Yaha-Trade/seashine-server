package com.seashine.server.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String userName) {
		return Jwts.builder().setSubject(userName).setExpiration(new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS512, this.secret.getBytes()).compact();
	}

	public boolean isValidToken(String authorization) {
		Claims claims = this.getClaims(authorization);
		if (claims != null) {
			String userName = claims.getSubject();
			Date expiration = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());

			if (userName != null && expiration != null && now.before(expiration)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String authorization) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(authorization).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUserName(String authorization) {
		Claims claims = this.getClaims(authorization);
		if (claims != null) {
			return claims.getSubject();
		}

		return null;
	}

}
