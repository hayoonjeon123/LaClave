package com.itwillbs.LaClave.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 테스트 편의를 위해 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup", "/loginProc", "/css/**", "/js/**", "/images/**", "/auth/**", "/").permitAll() // 메인, 회원가입 등은 누구나
                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 전용
                .anyRequest().authenticated() // 그 외 모든 페이지는 로그인 필수
            )
            .formLogin(form -> form
                .loginPage("/login") // 사용자가 만든 로그인 페이지가 있다면 설정
                .loginProcessingUrl("/loginProc") // 시큐리티가 로그인을 낚아챌 주소
                .usernameParameter("memberId") 
                .passwordParameter("memberPw") 
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 곳
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}