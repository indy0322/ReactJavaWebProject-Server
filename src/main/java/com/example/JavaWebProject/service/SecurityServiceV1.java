/*package com.example.JavaWebProject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service//만약 서비스가 제대로 실행이 안될시 @Service를 지우고 실행해본다.
public class SecurityServiceV1 implements SecurityService {

    private Long expireTimeMs = 1000 * 60 * 30L;

    private String token;

    @Override
    public String createToken(String userId, String key){
        Claims claims = Jwts.claims(); // 일종의 Map 이다.
        claims.put("userId", userId); //userId를 토큰에 추가하기

        token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    @Override
    public String getUserId(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId",String.class);
    }

    @Override
    public boolean isExpired(String token, String secretKey){
        //토큰의 만료시간이 지금보다 전 인가(예) 만료시간: 2023년 7월 21일 17시, 현재시간: 2023년 7월 21일 18시 => 토큰 만료됨.)
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    @Override
    public String getToken(){
        return token;
    }
}*/
