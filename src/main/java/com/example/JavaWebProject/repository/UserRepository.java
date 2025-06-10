package com.example.JavaWebProject.repository;

import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;

public interface UserRepository {
    User save(User user);

    String update(UserUpdateDto userUpdateDto);

    User search(String userId);

    String delete(String userId);
}
