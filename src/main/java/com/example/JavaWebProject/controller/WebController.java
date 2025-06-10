package com.example.JavaWebProject.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JavaWebProject.domain.SearchUser;
import com.example.JavaWebProject.domain.User;
import com.example.JavaWebProject.domain.UserUpdateDto;
import com.example.JavaWebProject.service.UserService;
import com.example.JavaWebProject.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;
    //private final SecurityService securityService;

    @GetMapping("/api/hello")
    public List<String> Hello(){
        return Arrays.asList("서버 포트는 8080", "리액트 포트는 3000");
    }

    @PostMapping("/api/register")
    public String register(@RequestBody User user){
        try{
            User existUser = userService.search(user.getUserId());
            if(existUser != null){
                return "존재하는 아이디 입니다";
            }
            userService.save(user);
            return user.getUserId();
        }
        catch(Exception e){
            System.out.println(e);
            return "아이디, 비밀번호를 제대로 입력해주세요";
        }
    }

    @PostMapping("/api/searchUser")
    public User searchUser(@RequestBody SearchUser userId){ //@RequestBody String userId 같이 매개변수를 지정하면 안된다.(매개변수의 객체는 User 같이 따라 만들어 주어야 한다.)
        User findUser = userService.search(userId.getUserId());
        return findUser;
    }

    @PostMapping("/api/updateUser")
    public String updateUser(@RequestBody UserUpdateDto updateDto){
        return userService.update(updateDto);
    }

    @PostMapping("/api/deleteUser")
    public String deleteUser(@RequestBody SearchUser userId){ //@RequestBody String userId 같이 매개변수를 지정하면 안된다.(매개변수의 객체는 User 같이 따라 만들어 주어야 한다.)
        return userService.delete(userId.getUserId());
    }

    /*@PostMapping("/api/login")
    public String login(@RequestBody User user){
        try{
            User findUser = userService.search(user.getUserId());
            if(findUser != null){
                String pass = String.valueOf(findUser.getUserPasswd());
                String pass2 = String.valueOf(user.getUserPasswd());
                if(pass.equals(pass2)){
                    log.info("사용자 비밀번호: {}",findUser.getUserPasswd());
                    log.info("입력한 비밀번호: {}",user.getUserPasswd());
                    return securityService.createToken(user.getUserId(),key);
                }
                else{
                    log.info("사용자 비밀번호: {}",findUser.getUserPasswd());
                    log.info("입력한 비밀번호: {}",user.getUserPasswd());
                    return "비빌번호가 다릅니다.";
                }
            }
            else{
                return "존재하지 않는 사용자 입니다.";
            }
        }catch (Exception e){
            log.error("로그인 에러: {}",e);
            return "로그인에 실패했습니다.";
        }
    }*/
}
