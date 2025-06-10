package com.example.JavaWebProject.domain;

import lombok.Data;

@Data
public class SearchUser {
    private String userId;

    public SearchUser(){

    }

    public SearchUser(String userId){
        this.userId = userId;
    }
}
