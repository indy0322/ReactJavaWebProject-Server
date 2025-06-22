package com.example.JavaWebProject.service;

import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;

public interface UserService {
    User save(User user);

    /*String update(UserUpdateDto userUpdateDto);*/

    String login(User user);

    User search(String userId);

    String delete(String userId);
}
