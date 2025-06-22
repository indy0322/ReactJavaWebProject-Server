package com.example.JavaWebProject.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.JavaWebProject.domain.User;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
    private final static Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();
    public final static String key = dotenv.get("JWT_SECRET");

    public static String create(User user){
        Date expiryDate = Date.from(
            Instant.now()
                .plus(1,ChronoUnit.DAYS)
        );

        Claims claims = Jwts.claims();
        claims.put("userId", user.getUserId());

        return Jwts.builder()
            .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
            .signWith(SignatureAlgorithm.HS512, key)
            .setSubject(user.getUserId())
            .setIssuer("demo")
            .setIssuedAt(new Date())    
            .setExpiration(expiryDate)
            .setClaims(claims)
            .compact();     
    }

    public String validateAndGetUserId(String token){//토큰이 유효한지 유효성을 검증하고 토큰 기반으로 userId를 가져오는 메서드
        Claims claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }
    
}
