package com.udayhostel.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{
	@Value("${jwt.secret}")
	private String secretKey;  
	
	private SecretKey getKey() 
	{
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));      
		
	}
	
	//Generate JWT
	public String generateToken(String username) 
	{
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getKey())
				.compact();
	}
	
	//Extract username from JWT
	public String extractUsername(String token) 
	{
		return Jwts.parser()
				   .verifyWith(getKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload()
				   .getSubject();
	}
	
	public boolean isTokenValid(
	        String token,
	        org.springframework.security.core.userdetails.UserDetails userDetails)
	{
	    String username = extractUsername(token);

	    return username.equals(userDetails.getUsername())
	            && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token)
	{
	    Date expiration = Jwts.parser()
	            .verifyWith(getKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .getExpiration();

	    return expiration.before(new Date());
	}
	
}