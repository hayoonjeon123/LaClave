package com.itwillbs.LaClave.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResponseDTO {
    // 리뷰 정보
    private Integer reviewIdx;
    private String content;
    private Double score;
    private LocalDateTime createdAt;

    // 상품 정보
    private Long productIdx;
    private String productName;
    private String imageUrl; // 상품 대표 이미지
    private String reviewImageUrl;  // 리뷰 이미지 추가

    // 구매 옵션 정보 (예: "Color: Black / Size: L")
    private String optionInfo;
    
    // 수정을 위한 주문 번호 (선택사항)
    private Integer ordersIdx;
}