package com.example.spring.util;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
	
	private JwtUtils() {}
	private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
	private static final String ISSUER = "codeing_streams_auth_server";

	public static boolean validateToken(String jwtToken) {
		// TODO Auto-generated method stub
		return parseToke(jwtToken).isPresent();
	}

	private static Optional<Claims> parseToke(String jwtToken) {
		var jwtParser = Jwts.parser()
			.verifyWith(secretKey)
			.build();
		try {
			return Optional.of(jwtParser.parseSignedClaims(jwtToken)
					.getPayload());
		} catch (JwtException | IllegalArgumentException e) {
			log.error("JWT Exception occurred");
		}
		return Optional.empty();
	}

	public static Optional<String> getUsernameFromToken(String jwtToken) {
		var claimOptional = parseToke(jwtToken);
		
		return claimOptional.map(Claims::getSubject);
	}

	public static String generateToken(String username) {
		var currentDate = new Date();
		var jwtExpirationInMinutes = 10;
		var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes); 
		
		return Jwts.builder()
			.id(UUID.randomUUID().toString())
			.issuer(ISSUER)
			.subject(username)
			.signWith(secretKey)
			.issuedAt(currentDate)
			.expiration(expiration)
			.compact();
	}

}
