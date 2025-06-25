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
                .plus(1,ChronoUnit.HOURS)//현재 시각으로부터 1시간 후의 Date 객체를 생성한다. 따라서 토큰의 만료시간은 1시간을 의미한다.
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

        return claims.getSubject();//claims.get("userId")로도 userId를 가져올 수 있다.(claims.put("userId", user.getUserId()); 코드 때문에)
    }

    public String getUserId(String token){//토큰 기반으로 userId를 가져오는 메서드
        Claims claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();


        return claims.getSubject();//claims.get("userId")로도 userId를 가져올 수 있다.(claims.put("userId", user.getUserId()); 코드 때문에)
    }

    public boolean isExpired(String token){// 토큰의 만료시간 확인
        return Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());//토큰의 만료시간이 현재 시간보다 이전인지 비교.
    }
    
}
