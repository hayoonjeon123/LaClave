package com.itwillbs.LaClave.AI;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Member.AiProfile;
import com.itwillbs.LaClave.Member.AiProfileRepository;
import com.itwillbs.LaClave.security.CustomUserDetails;
import com.itwillbs.LaClave.Cart.ItemImage;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Slf4j
public class AiController {

    private final ChatGPTService chatGPTService;
    private final ItemRepository itemRepository;
    private final AiProfileRepository aiProfileRepository;

    @GetMapping("/recommend")
    public List<AiItemDto> recommend(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("로그인이 필요한 서비스입니다.");
        }

        // 1. 사용자 AI 프로필 조회
        AiProfile profile = aiProfileRepository.findById(userDetails.getMemberIdx())
                .orElse(null);

        // 2. 전체 상품 조회
        List<Item> allItems = itemRepository.findAll();
        if (allItems.isEmpty())
            return new ArrayList<>();

        List<Item> recommendedEntityList = new ArrayList<>();

        if (profile != null && profile.getPrefStyles() != null && !profile.getPrefStyles().isBlank()) {
            // [키워드 매칭 방식] - 사용자가 선택한 키워드가 포함된 상품은 모두 추천
            String userPrefStyles = profile.getPrefStyles();
            log.info("사용자 취향 발견: {}", userPrefStyles);

            // 콤마로 구분된 스타일을 분리 (예: "빈티지,캐주얼" -> ["빈티지", "캐주얼"])
            String[] keywords = userPrefStyles.split(",");

            for (Item item : allItems) {
                boolean isMatched = false;
                String itemTags = item.getStyleTags(); // 예: "#빈티지 #모던"

                if (itemTags != null) {
                    for (String keyword : keywords) {
                        String cleanKeyword = keyword.trim();
                        if (!cleanKeyword.isEmpty() && itemTags.contains(cleanKeyword)) {
                            // 태그에 키워드가 하나라도 포함되면 매칭 성공
                            isMatched = true;
                            break;
                        }
                    }
                }

                if (isMatched) {
                    recommendedEntityList.add(item);
                }
            }

            // 만약 매칭된 상품이 하나도 없다면? -> 기존처럼 AI에게 트렌드 추천 요청 (선택 사항)
            if (recommendedEntityList.isEmpty()) {
                log.info("매칭된 상품이 없어 AI 트렌드 추천으로 대체합니다.");
                recommendByTrend(recommendedEntityList);
            } else {
                log.info("키워드 매칭 결과 {}개의 상품을 찾았습니다.", recommendedEntityList.size());
            }

        } else {
            // [취향 정보 없을 때 - 기존 트렌드 방식 유지]
            log.info("취향 정보가 없어 AI 트렌드 추천을 진행합니다.");
            recommendByTrend(recommendedEntityList);
        }

        // DTO 변환
        return recommendedEntityList.stream().map(item -> {
            List<Map<String, String>> imageList = new ArrayList<>();
            if (item.getImages() != null) {
                item.getImages().forEach(img -> {
                    Map<String, String> imgMap = new HashMap<>();
                    imgMap.put("imagePath", img.getUrl());
                    imageList.add(imgMap);
                });
            }

            return new AiItemDto(
                    item.getProductIdx(),
                    item.getProductName(),
                    item.getProductPrice(),
                    item.getProductDiscountRate(),
                    imageList);
        }).collect(Collectors.toList());
    }

    // AI 트렌드 추천 로직 분리
    private void recommendByTrend(List<Item> targetList) {
        String userQuery = "요즘 유행하는 트렌디한 옷 스타일 검색 키워드를 3개만 추천해줘. 부가 설명 없이 한국어 단어로만 쉼표(,)로 구분해서 줘.";
        String gptResponse = chatGPTService.askChatGPT(userQuery);
        String[] keywords = gptResponse.split(",");

        Set<Item> resultItems = new HashSet<>();
        for (String keyword : keywords) {
            if (!keyword.trim().isEmpty()) {
                resultItems.addAll(itemRepository.findByProductNameContainingOrStyleTagsContaining(keyword.trim(),
                        keyword.trim()));
                if (resultItems.size() >= 10)
                    break;
            }
        }
        targetList.addAll(resultItems);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AiItemDto {
        private Long productIdx;
        private String productName;
        private int productPrice;
        private int productDiscountRate;
        private List<Map<String, String>> images;
    }
}
