package com.itwillbs.LaClave.AI;

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
	
	// ObjectMapper 객체 주입
	private final ObjectMapper mapper;
	
	// ChatGPTClient 주입
	@Autowired
	private ChatGPTClient chatGPTClient;
	
	// GPT-API 호출하는 동작 (컨트롤러에서 호출)
	public String askChatGPT(String prompt) {
		log.info(" 서비스 - GPT 호출 ");
		
		String result = chatGPTClient.askChatGPT(prompt);
		
// [result결과값]
//		responseEntity : {
//			  "id": "chatcmpl-Cx2AI8d8vhCXPN7eb4oRY5VSQO2c3",
//			  "object": "chat.completion",
//			  "created": 1768185710,
//			  "model": "gpt-4o-mini-2024-07-18",
//			  "choices": [
//			    {
//			      "index": 0,
//			      "message": {
//			        "role": "assistant",
//			        "content": "김치찌개와 밥, 또는 비빔밥이 좋습니다. 간단하면서도 맛있어요!",
//			        "refusal": null,
//			        "annotations": []
//			      },
//			      "logprobs": null,
//			      "finish_reason": "stop"
//			    }
//			  ],
//			  "usage": {
//			    "prompt_tokens": 35,
//			    "completion_tokens": 26,
//			    "total_tokens": 61,
//			    "prompt_tokens_details": {
//			      "cached_tokens": 0,
//			      "audio_tokens": 0
//			    },
//			    "completion_tokens_details": {
//			      "reasoning_tokens": 0,
//			      "audio_tokens": 0,
//			      "accepted_prediction_tokens": 0,
//			      "rejected_prediction_tokens": 0
//			    }
//			  },
//			  "service_tier": "default",
//			  "system_fingerprint": "fp_29330a9688"
//			}
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contentText;
	}
	
	

}
