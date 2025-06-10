package com.example.JavaWebProject.domain;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String userId;
    private String userPasswd;
    private String searchId;

    public UserUpdateDto(){}

    public UserUpdateDto(String searchId, String userId, String userPasswd){
        this.searchId = searchId;
        this.userId = userId;
        this.userPasswd = userPasswd;
    }
}
