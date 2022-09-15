package com.dev.nou.springjwt.util;

import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${app.secret.key}")
	private String secret_key;
	
	//gen token
	
	public String generateToken(String subject) {
		String tokenId=String.valueOf(new Random().nextInt(10000));
		return Jwts.builder().setId(tokenId)
				.setSubject(subject)
				.setIssuer("Nou INC")
				.setAudience("SHINNIX_Ltd")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret_key.getBytes()))
				.compact();
	}
	//get claims
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encode(secret_key.getBytes()))
				.parseClaimsJws(token)
				.getBody()
				;
	}
	//check if token is valid
	public boolean isTokenValid(String token) {
		return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
		
	}
	//check if token is valid as per username
	public boolean isTokenValid(String token, String username) {
		String tokenUsername=getSubject(token);
		return (username.equals(tokenUsername)&&isTokenValid(token));
		
	}
	//  check if token is expired
		public boolean isTokenExpired(String token) {
			return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
		}
		//code to get expiration date
	public Date getExpirationDate(String token) {
		// TODO Auto-generated method stub
		return getClaims(token).getExpiration();
	}
	//get expiration date
	public String getSubject(String token) {
		// TODO Auto-generated method stub
		return getClaims(token).getSubject();
	}
}
