package com.example.JavaWebProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @Column(name = "Id")
    @NotBlank
    private String userId;

    @Column(name = "Password")
    @NotBlank
    private String userPasswd;

}
