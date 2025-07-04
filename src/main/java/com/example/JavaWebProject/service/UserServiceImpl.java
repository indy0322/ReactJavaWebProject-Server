package com.example.JavaWebProject.service;

import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;
import com.example.JavaWebProject.repository.UserRepository;
import com.example.JavaWebProject.util.JwtUtil;
import com.example.JavaWebProject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }


    /*@Override
    public String update(UserUpdateDto updateDto){
        return userRepository.update(updateDto);
    }*/

    @Override
    public String login(User user){
        return jwtUtil.create(user);
    }

    @Override
    public User search(String userId){
       return userRepository.search(userId);
    }

    @Override
    public String delete(String userId){
        return userRepository.delete(userId);
    }
    
}
