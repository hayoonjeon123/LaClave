//package com.itwillbs.LaClave.AI;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.itwillbs.LaClave.Category.Item;
//
//import lombok.extern.log4j.Log4j2;
//
//import java.util.List;
//
//@Component
//@Log4j2
//public class DataInitializer implements CommandLineRunner {
//
//    private final ProductRepository productRepository;
//    private final AiRecommendationService aiService;
//
//    public DataInitializer(ProductRepository productRepository, AiRecommendationService aiService) {
//        this.productRepository = productRepository;
//        this.aiService = aiService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 1. DB에서 벡터 데이터가 비어있는 상품들만 싹 가져옵니다.
//        List<Item> emptyVectorProducts = productRepository.findAllByProductVectorIsNull();
//        log.info("조회된 빈 상품 개수:" + emptyVectorProducts.size());
//        
//        if (emptyVectorProducts.isEmpty()) {
//            System.out.println(">>> 모든 상품이 이미 벡터화되어 있습니다. 작업을 건너뜁니다.");
//            return;
//        }
//
//        System.out.println(">>> 총 " + emptyVectorProducts.size() + "개의 상품 벡터화 작업을 시작합니다.");
//
//        for (Item i : emptyVectorProducts) {
//            try {
//                String combinedText = i.getProductName() + " " 
//                                    + i.getProductShortDesc() + " " 
//                                    + i.getStyleTags();
//                
//                // 3. AI 서비스에게 보내서 숫자(JSON)로 바꿉니다.
//                String vectorJson = aiService.EmbeddingAsJson(combinedText);
//                
//                // 4. 받아온 숫자를 상품의 벡터 필드에 넣고 DB에 저장합니다.
//                i.setProductVector(vectorJson);
//                productRepository.save(i);
//                
//                System.out.println(">>> [" + i.getProductName() + "] 벡터화 완료!");
//                
//                // API 호출 과부하 방지를 위해 아주 잠깐 쉽니다.
//                Thread.sleep(100); 
//            } catch (Exception e) {
//                System.err.println(">>> 에러 발생 (" + i.getProductName() + "): " + e.getMessage());
//            }
//        }
//        System.out.println(">>> 모든 작업이 완료되었습니다!");
//    }
//}