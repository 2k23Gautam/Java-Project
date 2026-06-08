package com.Gautam.journalApp.utilies;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Component
public class JwtUtil {

    private String SECRET_KEY = "qwert13245@#$%gewrj2u3428%$#^&*UJEy";

    public String generateToken(String username){
        //payload
        Map<String ,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    public String createToken(Map<String,Object>claims,String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*1))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
