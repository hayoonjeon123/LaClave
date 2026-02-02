package com.itwillbs.LaClave.category;

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
	
    private Double averageScore;
    
    private List<ReviewDetail> reviewList; 

   
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
            this.imageUrls = new ArrayList<>();
        }
    }
}
