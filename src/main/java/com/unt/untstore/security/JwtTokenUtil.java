package com.unt.untstore.security;

import com.unt.untstore.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.tokenExpiry}")
	private Long expiration;

	public String generateToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getEmailId());
		claims.put("lastName", user.getFirstName());
		claims.put("firstName", user.getLastName());
		return generateToken(claims);
	}

	private String generateToken(Claims claims) {
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	public Claims getAllCliamsFromToken(String token) {
		return getClaimsFromToken(token);
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Boolean isTokenExpired(final Date expiration) {
		return expiration.before(new Date());
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public Boolean validateToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		final String username = claims.getSubject();
		final Date expiryDate = claims.getExpiration();
		return username != null && !isTokenExpired(expiryDate);
	}
}
