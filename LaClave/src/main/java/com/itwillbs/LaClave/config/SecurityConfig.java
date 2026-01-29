package com.itwillbs.LaClave.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(request -> {
					var config = new org.springframework.web.cors.CorsConfiguration();
					config.setAllowedOriginPatterns(java.util.List.of("http://localhost:517*")); 
					config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(java.util.List.of("*"));
					config.setAllowCredentials(true);
					return config;
				}))
				.sessionManagement(session -> session
						.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
				)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/error", "/images/**", "/loginProc", "/api/loginProc", "/api/signup",
								"/api/email-send", "/api/email-verify", "/api/check-id", "/api/check-email",
								"/api/find-id", "/api/find-pw",
								"/api/category/**", "/api/product/**", "/api/products/**", "/api/items/**", "/api/search/**",
								"/api/ai/recommend/**"
						).permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginProcessingUrl("/api/loginProc")
						.usernameParameter("memberId")
						.passwordParameter("memberPw")
						.successHandler((request, response, authentication) -> {
							
							jakarta.servlet.http.HttpSession session = request.getSession(true);
							session.setAttribute("SPRING_SECURITY_CONTEXT", org.springframework.security.core.context.SecurityContextHolder.getContext());
							
							response.setStatus(200);
							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().write("{\"status\":\"success\"}");
						})
						.failureHandler((request, response, exception) -> {
							response.setStatus(401);
							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().write("{\"status\":\"fail\"}");
						})
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/api/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setStatus(200);
							response.getWriter().write("{\"status\":\"logout success\"}");
						}))
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