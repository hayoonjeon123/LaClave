package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정 클래스임을 명시
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class SecurityConfig   {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .csrf().disable()
            .formLogin().disable();

        return httpSecurity
            .authorizeHttpRequests(
                authorize -> authorize
                    .requestMatchers("/users/**").permitAll()
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated()
            )
            .build();
    }

		
}
