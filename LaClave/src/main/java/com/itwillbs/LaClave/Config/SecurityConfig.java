package com.itwillbs.LaClave.Config;

import org.modelmapper.ModelMapper;
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
	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(request -> {
					var config = new org.springframework.web.cors.CorsConfiguration();
					config.setAllowedOriginPatterns(java.util.List.of("http://localhost:517*")); // 리액트 주소 패턴
					config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(java.util.List.of("*")); // 모든 헤더 허용
					config.setAllowCredentials(true); // 세션/쿠키 허용
					return config;
				}))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/signup", "/loginProc", "/email-send", "/email-verify",
								"/api/my/**")
						.permitAll()
						.requestMatchers("/category/**", "/product/**", "/api/category/**", "/api/product/**",
								"/api/review/**")
						.permitAll()
						.anyRequest().authenticated()

				)
				.formLogin(form -> form
						.loginProcessingUrl("/loginProc")
						.usernameParameter("memberId")
						.passwordParameter("memberPw")
						// 성공 시 리다이렉트 대신 200 OK 응답 (리액트용)
						.successHandler((request, response, authentication) -> {
							response.setStatus(200);
							response.getWriter().write("{\"status\":\"success\"}");
						})
						// 실패 시 401 Unauthorized 응답
						.failureHandler((request, response, exception) -> {
							response.setStatus(401);
							response.getWriter().write("{\"status\":\"fail\"}");
						})
						.permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/"))
				.userDetailsService(customUserDetailsService); // 여기서 등록
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}