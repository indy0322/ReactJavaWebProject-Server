package com.example.JavaWebProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.JavaWebProject.util.JwtFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(http -> http.disable())//브라우저 팝업으로 ID/PW 입력하는 HTTP Basic 인증 방식 비활성화, 우리는 대신 JWT로 인증하므로 필요 없음
                .csrf(csrf -> csrf.disable())//CSRF 보호 비활성화, REST API(특히 토큰 기반 인증)에선 보통 CSRF 사용 안 함
                .cors(Customizer.withDefaults()) //CORS(Cross-Origin Resource Sharing) 허용, 프론트엔드(React, port 3000) → 백엔드(Spring, port 8080) 요청 허용, @CrossOrigin 없이도 작동하려면 별도로 CorsConfigurationSource 설정해주는 것도 가능
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Spring Security에서 세션을 아예 사용하지 않음, 로그인을 해도 세션 생성하지 않고, 매 요청마다 JWT 토큰으로 인증
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/api/login","/api/register").permitAll()// 전체 접근을 허용
                    .anyRequest().authenticated()///, /api/** 경로는 누구나 접근 허용 (permitAll()) 그 외 나머지 모든 경로는 인증 필요 (authenticated())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //jwtFilter를 인증 필터 체인에 앞단에 삽입, JWT 토큰을 먼저 확인해서 사용자 인증을 시도
                .build();
    }
}
