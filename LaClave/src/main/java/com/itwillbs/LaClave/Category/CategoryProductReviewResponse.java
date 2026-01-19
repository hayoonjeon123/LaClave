package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.itwillbs.LaClave.review.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductReviewResponse {
    // 1. 전체 데이터 세트를 담을 필드들
    private Double averageScore;
    // 평균 점수
    private List<ReviewDetail> reviewList; // 리뷰 리스트 (아래 내부 클래스 사용)

    // 2. 리뷰 한 건 한 건의 정보를 담을 내부 클래스 (파일 하나에 다 들어감)

    @Data
    public static class ReviewDetail {
        private Integer reviewIdx;
        private String nickname;
        private String content;
        private Double score;
        private List<String> imageUrls;

        public ReviewDetail(Review review) {
            this.reviewIdx = review.getReviewIdx();
            this.nickname = (review.getMember() != null) ? review.getMember().getNickname() : "익명";
            this.content = review.getContent();
            this.score = review.getScore();

            // 이미지 URL 리스트 처리
            // 이미지 URL 리스트 처리 (임시 비활성화)
            // if (review.getImages() != null) {
            // this.imageUrls = review.getImages().stream()
            // .map(image -> image.getUrl())
            // .collect(Collectors.toList());
            // } else {
            this.imageUrls = new ArrayList<>();
            // }
        }
    }
}
