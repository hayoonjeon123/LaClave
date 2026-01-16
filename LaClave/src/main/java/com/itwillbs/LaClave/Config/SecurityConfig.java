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
					config.setAllowedHeaders(java.util.List.of("*"));
					config.setAllowCredentials(true);
					return config;
				}))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/login", "/signup", "/loginProc",
								"/email-send", "/email-verify",
								"/api/my/**", "/api/orders",
								"/category/**", "/product/**",
								"/api/category/**", "/api/product/**",
								"/api/review/**",
								"/api/cart/**", "/cart/**",
								"/api/orders/create", "/api/orders/**",
								"/orders/**" // 프록시가 /api 제거하므로 추가
						).permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginProcessingUrl("/loginProc")
						.usernameParameter("memberId")
						.passwordParameter("memberPw")
						.successHandler((request, response, authentication) -> {
							response.setStatus(200);
							response.getWriter().write("{\"status\":\"success\"}");
						})
						.failureHandler((request, response, exception) -> {
							response.setStatus(401);
							response.getWriter().write("{\"status\":\"fail\"}");
						})
						.permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/"))
				.userDetailsService(customUserDetailsService);

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