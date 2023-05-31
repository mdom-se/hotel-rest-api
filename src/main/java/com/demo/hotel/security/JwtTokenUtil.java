package com.demo.hotel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
  private static final int expireInMs = 30_000 * 1000;

  private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String generate(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuer("hotel.demo.com")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
        .signWith(key)
        .compact();
  }
  public boolean validate(String token) {
    if (getUsername(token) != null && isExpired(token)) {
      return true;
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    return claims.getSubject();
  }

  public boolean isExpired(String token) {
    Claims claims = getClaims(token);
    return claims.getExpiration().after(new Date(System.currentTimeMillis()));
  }

  private Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
  }
}