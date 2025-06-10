package com.example.JavaWebProject.service;

import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;

public interface UserService {
    User save(User user);

    String update(UserUpdateDto userUpdateDto);

    User search(String userId);

    String delete(String userId);
}
