package com.example.JavaWebProject.repository;

import org.springframework.transaction.annotation.Transactional;

import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    
    private final JpaUserRepository repository;


    @Override
    public User save(User user){
        return repository.save(user);
    }

    @Override
    public String update(UserUpdateDto userUpdateDto){
        repository.updateUser(userUpdateDto.getUserId(), userUpdateDto.getUserPasswd(), userUpdateDto.getSearchId());
        return "갱신 완료";
    }

    @Override
    public User search(String userId){
        return repository.findByUsers(userId);
    }

    @Override
    public String delete(String userId){
        repository.delete(userId);
        return "삭제 완료";
    }
    
}
