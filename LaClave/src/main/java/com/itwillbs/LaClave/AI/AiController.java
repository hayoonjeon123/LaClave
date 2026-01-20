//package com.itwillbs.LaClave.AI;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itwillbs.LaClave.Category.Item;
//
//@RestController
//@RequestMapping("/api/ai")
//public class AiController {
//
//    private final AiRecommendationService aiRecommendationService;
//
//    public AiController(AiRecommendationService aiRecommendationService) {
//        this.aiRecommendationService = aiRecommendationService;
//    }
//
//    @GetMapping("/recommend")
//    public String test(@RequestParam String keywords) {
//        // 사용자가 선택한 키워드(예: "빈티지, 러블리")를 받아 추천 리스트 반환
//        return aiRecommendationService.EmbeddingAsJson(keywords);
//    }
//    
//}
