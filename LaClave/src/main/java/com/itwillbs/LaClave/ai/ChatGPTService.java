package com.itwillbs.LaClave.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatGPTService {
	
	private final ObjectMapper mapper;
	
	@Autowired
	private ChatGPTClient chatGPTClient;
	
	public String askChatGPT(String prompt) {
		log.info(" 서비스 - GPT 호출 ");
		
		String result = chatGPTClient.askChatGPT(prompt);

		String contentText = "";
		
		try {
			// 문자열 (JSON) -> 트리 구조로 파싱
			JsonNode root = mapper.readTree(result);
			contentText = root.path("choices")
							  .path(0)
							  .path("message")
							  .path("content")
							  .asText();
						
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return contentText;
	}
	
	

}



