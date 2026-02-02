package com.itwillbs.LaClave.ai;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.category.Item;
import com.itwillbs.LaClave.category.ItemRepository;
import com.itwillbs.LaClave.config.CustomUserDetails;
import com.itwillbs.LaClave.member.AiProfile;
import com.itwillbs.LaClave.member.AiProfileRepository;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Slf4j
public class AiController {

    private final ChatGPTService chatGPTService;
    private final ItemRepository itemRepository;
    private final AiProfileRepository aiProfileRepository;

    @GetMapping("/recommend")
    public List<AiItemDto> recommend(@AuthenticationPrincipal CustomUserDetails userDetails) {
        AiProfile profile = aiProfileRepository.findById(userDetails.getMemberIdx())
                .orElse(null);

        List<Item> allItems = itemRepository.findAll();
        if (allItems.isEmpty())
            return new ArrayList<>();

        List<Item> recommendedEntityList = new ArrayList<>();

        if (profile != null && profile.getPrefStyles() != null && !profile.getPrefStyles().isBlank()) {
            String userPrefStyles = profile.getPrefStyles();

            String[] keywords = userPrefStyles.split(",");

            for (Item item : allItems) {
                boolean isMatched = false;
                String itemTags = item.getStyleTags(); 

                if (itemTags != null) {
                    for (String keyword : keywords) {
                        String cleanKeyword = keyword.trim();
                        if (!cleanKeyword.isEmpty() && itemTags.contains(cleanKeyword)) {
                            isMatched = true;
                            break;
                        }
                    }
                }

                if (isMatched) {
                    recommendedEntityList.add(item);
                }
            }

            if (recommendedEntityList.isEmpty()) {
                log.info("매칭된 상품이 없어 AI 트렌드 추천으로 대체합니다.");
                recommendByTrend(recommendedEntityList);
            } else {
            }

        } else {
            log.info("취향 정보가 없어 AI 트렌드 추천을 진행합니다.");
            recommendByTrend(recommendedEntityList);
        }

        return recommendedEntityList.stream().map(item -> {
            List<Map<String, String>> imageList = new ArrayList<>();
            String mainImageUrl = null;

            if (item.getImages() != null && !item.getImages().isEmpty()) {
                // img_01(대표 이미지) 우선 순위로 찾기
                for (com.itwillbs.LaClave.image.Image img : item.getImages()) {
                    String url = img.getUrl();
                    if (url == null || url.isEmpty()) continue;

                    // 리뷰 이미지는 제외
                    if ("REVIEW".equals(img.getTargetType()) || "img_04".equals(img.getTargetCode())) {
                        continue;
                    }

                    // /images/ 접두사 처리
                    if (!url.startsWith("http") && !url.startsWith("/images/")) {
                        url = "/images/" + url;
                    }

                    if ("img_01".equals(img.getTargetCode())) {
                        mainImageUrl = url;
                        break;
                    }
                    if (mainImageUrl == null) {
                        mainImageUrl = url;
                    }
                }
            }

            if (mainImageUrl != null) {
                Map<String, String> imgMap = new HashMap<>();
                imgMap.put("imagePath", mainImageUrl);
                imageList.add(imgMap);
            }

            return new AiItemDto(
                    item.getProductIdx(),
                    item.getProductName(),
                    item.getProductPrice(),
                    imageList,
                    mainImageUrl);
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
        private List<Map<String, String>> images;
        private String productImageUrl;
    }
}
