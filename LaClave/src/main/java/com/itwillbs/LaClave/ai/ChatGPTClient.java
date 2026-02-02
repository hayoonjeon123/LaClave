package com.itwillbs.LaClave.ai;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

/**
 * ChatGPTClient
 * -> 외부 API 통신을 처리하는 객체
 * https://platform.openai.com/docs/overview
 * 
 */
@Component
@Log4j2
public class ChatGPTClient {

	@Value("${gpt.api-key}")
	private String apiKey; // API KEY값
	@Value("${gpt.api-url}")
	private String url; // API 요청주소
	@Value("${gpt.api-model}")
	private String model; // GPT 모델정보
	@Value("${gpt.api-temperature}")
	private double temperature; // 답변온도

	public String askChatGPT(String prompt) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.set("Authorization", "Bearer " + apiKey);

		Map<String, String> roleSystem = new HashMap<>();
		roleSystem.put("role", "system");
		String systemPrompt = "너는 쇼핑몰 패션 추천 AI야. 사용자의 취향이나 요청을 듣고, " +
				"우리 데이터베이스에서 검색할 수 있는 구체적인 상품 검색 키워드(예: 원피스, 셔츠, 슬랙스, 빈티지, 러블리 등)를 " +
				"1개에서 3개 사이로 쉼표(,)로 구분해서 한국어로만 알려줘. " +
				"다른 미사여구 없이 오직 단어만 반환해.";
		roleSystem.put("content", systemPrompt);

		Map<String, String> roleUser = new HashMap<>();
		roleUser.put("role", "user");
		roleUser.put("content", prompt);

		List<Map<String, String>> messages = new ArrayList<>();
		messages.add(roleSystem);
		messages.add(roleUser);

		Map<String, Object> requestData = new HashMap<>();
		requestData.put("model", model);
		requestData.put("temperature", temperature);
		requestData.put("messages", messages);

		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(requestData, headers);

		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		messageConverters.addAll(restTemplate.getMessageConverters());
		restTemplate.setMessageConverters(messageConverters);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

		log.info("responseEntity : {}", responseEntity);
		log.info("responseEntity : {}", responseEntity.getBody());

		return responseEntity.getBody();
	}

}



