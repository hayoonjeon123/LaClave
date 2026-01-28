package com.itwillbs.LaClave.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



// servlet-context.xml (WEB과 관련된 설정)

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
	
	
	// CORS(Cross-Origin Resource Sharing)
	// -> 교차 출러 리소스 공유
	
	// 리액트 - 스프링부트랑 통신을 하기위해서 필요한 설정
	// 1) 컨트롤러(@Controller) 클래스에  @CrossOrigin  어노테이션 설정(전부) 
	// 2) SpringSecurity를 사용해서 처리가능
	// 3) WebMvcConfigurer를 사용해서 설정 제어
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")			// 어떤 주소(URL)에 접속 허용여부   /** 모든주소
//		        .allowedOrigins("*")   		// 어떤 도메인에서 접근 혀용여부	* 모든출처 (개발용)
//		        .allowedOrigins("*")   		// 어떤 도메인에서 접근 혀용여부	* 모든출처 (개발용)
				.allowedOriginPatterns("http://localhost:517*")
		        //.allowedOrigins("https://myapp.com","https://admin.myapp.com")    (실제 운영)
		        .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")   // 허용하는 HTTP 메서드의 종류
		        .allowCredentials(true)
		        .maxAge(300)                // Preflight (OPTIONS) 요청시 결과 처리 캐싱 시간(초)
		        .allowedHeaders("Authorization","Cache-Control","Content-Type"); // 요청시 허용할 해더 정보
		        
		
		
	}
	
	
	
	

	
	
	
	
}
