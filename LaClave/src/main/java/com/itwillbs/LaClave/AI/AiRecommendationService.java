//package com.itwillbs.LaClave.AI;
//
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.stereotype.Service;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//// OpenAI 호출 및 사용자 취향과 상품을 비교
//@Service
//public class AiRecommendationService {
//
//    private final EmbeddingModel embeddingModel;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @org.springframework.beans.factory.annotation.Autowired
//    public AiRecommendationService(EmbeddingModel embeddingModel) {
//        this.embeddingModel = embeddingModel;
//    }
//
//    // 1. 텍스트를 숫자로 변환
//    public String EmbeddingAsJson(String text) {
//        float[] embedding = embeddingModel.embed(text);
//        try {
//            return objectMapper.writeValueAsString(embedding);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("JSON 변환 실패", e);
//        }
//    }
//
//    // 2. 두 벡터 사이의 유사도 계산
//    public double calculateSimilarity(String vectorJson1, String vectorJson2) {
//        try {
//            float[] v1 = objectMapper.readValue(vectorJson1, float[].class);
//            float[] v2 = objectMapper.readValue(vectorJson2, float[].class);
//
//            double dotProduct = 0;
//            double normA = 0;
//            double normB = 0;
//            for (int i = 0; i < v1.length; i++) {
//                dotProduct += v1[i] * v2[i];
//                normA += Math.pow(v1[i], 2);
//                normB += Math.pow(v2[i], 2);
//            }
//            return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//}
