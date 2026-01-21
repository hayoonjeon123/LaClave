//package com.itwillbs.LaClave.AI;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import lombok.extern.log4j.Log4j2;
//
///**
// * ChatGPTClient
// * -> 외부 API 통신을 처리하는 객체
// * https://platform.openai.com/docs/overview
// * 
// */
//@Component
//@Log4j2
//public class ChatGPTClient {
//
//	@Value("${gpt.api-key}")
//	private String apiKey; // API KEY값
//	@Value("${gpt.api-url}")
//	private String url; // API 요청주소
//	@Value("${gpt.api-model}")
//	private String model; // GPT 모델정보
//	@Value("${gpt.api-temperature}")
//	private double temperature; // 답변온도
//
//	// REST API 방식을 사용, 외부 호출을 통한 데이터(HTTP)통신
//	public String askChatGPT(String prompt) {
//
//		// 1) Header 정보 설정
//		// org.springframework.http.HttpHeaders
//		HttpHeaders headers = new HttpHeaders();
//		// 헤더 정보 저장 - ContentType
//		// -H "Content-Type: application/json" \
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		// -H "Authorization: Bearer $OPENAI_API_KEY" \
//		headers.set("Authorization", "Bearer " + apiKey);
//
//		// 2) 요청 파라메터 생성(JSON타입)
//		// => AI 포지션을 결정하는 동작
//
//		// AI 시스템설정
//		Map<String, String> roleSystem = new HashMap<>();
//		roleSystem.put("role", "system");
//		// roleSystem.put("content", "AI 지켜야할 규칙, 답변형태, 설정...");
//		// ex) 영화서비스 -> 추천
//		// "너는 영화 서비스 안내원 역할을 수행할것, 우리 서비스에서 영화정보를 주면
//		// 거기에 해당하는 해시코드를 10개 생성해라"
//		// " 해시코드 1개당 최대 5글자로 표현, 공백 X,설명X 해시코드만 생성"
//		// roleSystem.put("content"," 내가 질문한 답변을 100자 이내로 답변해줘 ");
//		// 수정: 패션 검색 키워드 추출 전문가 역할
//		String systemPrompt = "너는 쇼핑몰 패션 추천 AI야. 사용자의 취향이나 요청을 듣고, " +
//				"우리 데이터베이스에서 검색할 수 있는 구체적인 상품 검색 키워드(예: 원피스, 셔츠, 슬랙스, 빈티지, 러블리 등)를 " +
//				"1개에서 3개 사이로 쉼표(,)로 구분해서 한국어로만 알려줘. " +
//				"다른 미사여구 없이 오직 단어만 반환해.";
//		roleSystem.put("content", systemPrompt);
//
//		// 사용자 요청정보
//		Map<String, String> roleUser = new HashMap<>();
//		roleUser.put("role", "user");
//		roleUser.put("content", prompt);
//
//		// 시스템 정보설정 + 사용자 요청정보 => 저장
//		List<Map<String, String>> messages = new ArrayList<>();
//		messages.add(roleSystem);
//		messages.add(roleUser);
//
//		// 모델정보, 답변 온도, 메세지 정보(시스템 정보 + 사용자 요청) 저장해서 전달
//		// => JSON형태
//
//		Map<String, Object> requestData = new HashMap<>();
//		requestData.put("model", model);
//		requestData.put("temperature", temperature);
//		requestData.put("messages", messages);
//
//		// -d '{
//		// "model": "gpt-5-nano",
//		// "input": "Write a one-sentence bedtime story about a unicorn."
//		// }'
//		// HTTP 요청 정보를 처리하는 객체 (HttpEntity)
//		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(requestData, headers);
//
//		// RESTAPI 요청 => RestTemplate 객체
//		RestTemplate restTemplate = new RestTemplate();
//
//		// UTF-8 설정
//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
//		messageConverters.addAll(restTemplate.getMessageConverters());
//		restTemplate.setMessageConverters(messageConverters);
//
//		// API 호출 - HTTP method 상관없이 호출
//		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//		// 호출주소, 호출방식, 전달정보, 리턴 타입
//
//		log.info("responseEntity : {}", responseEntity);
//		log.info("responseEntity : {}", responseEntity.getBody());
//
//		return responseEntity.getBody();
//	}
//
//}
