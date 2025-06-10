package com.example.JavaWebProject.service;

public interface SecurityService {
    String createToken(String userId, String key);

    String getUserId(String token, String secretKey);

    boolean isExpired(String token, String secretKey);

    String getToken();
}
