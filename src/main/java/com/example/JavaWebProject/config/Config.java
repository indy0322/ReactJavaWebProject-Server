package com.example.JavaWebProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.JavaWebProject.repository.JpaUserRepository;
import com.example.JavaWebProject.repository.UserRepository;
import com.example.JavaWebProject.repository.UserRepositoryImpl;
import com.example.JavaWebProject.service.UserService;
import com.example.JavaWebProject.service.UserServiceImpl;
import com.example.JavaWebProject.util.JwtFilter;
import com.example.JavaWebProject.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final JpaUserRepository jpaUserRepository;
    private final JwtUtil jwtUtil;

    @Bean
    public UserService userService(){
        return new UserServiceImpl(userRepository(),jwtUtil);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(jpaUserRepository);
    }
}
