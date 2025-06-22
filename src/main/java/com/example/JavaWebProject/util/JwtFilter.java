package com.example.JavaWebProject.util;

import java.io.IOException;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtFilter extends OncePerRequestFilter{
    
    @Autowired //의존성 주입 방법
    private JwtUtil jwtUtil;
    
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        try{
            String token = parseBearerToken(request);
            
            if(token != null && !token.equalsIgnoreCase("null")){//HTTP 요청에서 JWT 토큰을 꺼내서, null이나 "null" 문자열이 아닌 경우에만 처리한다
                String userId = jwtUtil.validateAndGetUserId(token);

                AbstractAuthenticationToken authentiacation = new UsernamePasswordAuthenticationToken(userId,null,List.of(new SimpleGrantedAuthority("USER"))); //userId를 주체(Principal)로 사용해서 Authentication 객체를 만든다, 비밀번호는 null, AuthorityUtils.NO_AUTHORITIES는 권한이 없음을 의미 (나중에 ROLE_USER 같은 권한을 넣을 수 있음)
                authentiacation.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //현재 요청의 세부 정보를 인증 객체에 연결한다, IP 주소, 세션 ID 등의 정보가 포함된다.
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentiacation);
                SecurityContextHolder.setContext(securityContext);

                /*@GetMapping("/me")
                public String getUser(@AuthenticationPrincipal String userId) {
                    return "로그인한 사용자: " + userId;
                } 또는 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String userId = (String) auth.getPrincipal();로 사용자 정보를 꺼낼 수 있다.*/
            }
        }catch(Exception e){
            logger.error("Could not set uer authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
        //Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")){ //문자열이 null이 아니고, 공백이 아닌 글자가 하나라도 있는지, 문자열이 "Bearer "로 시작하는지를 확인
            return bearToken.substring(7); //"Bearer "는 7글자이므로, 앞부분 "Bearer "를 제거하고 순수 토큰 값만 리턴
        }
        return null;
    }
}
