package com.itwillbs.LaClave.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지용 리뷰 DTO
 * - 작성 완료 리뷰 및 작성 가능 리뷰 공용
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long reviewIdx;      // 작성 완료 리뷰면 실제 reviewIdx, 작성 가능 리뷰면 0
    private Long productIdx;
    private String productName;
    private String imageUrl;
    private String optionInfo;   // 색상/사이즈 정보
    private String content;      // 작성 완료 리뷰면 내용, 작성 가능 리뷰면 ""
    private int score;           // 작성 완료 리뷰면 별점, 작성 가능 리뷰면 0
    private Long ordersIdx;      // 리뷰 작성 시 필요한 주문 번호
}
