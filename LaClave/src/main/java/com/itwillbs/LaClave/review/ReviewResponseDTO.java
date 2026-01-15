package com.itwillbs.LaClave.review;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long productIdx;
    private String productName;
    private List<String> options; // 옵션명 리스트
    private List<ReviewDTO> reviews;

    @Data
    @AllArgsConstructor
    public static class ReviewDTO {
        private Integer reviewIdx;
        private Integer memberIdx;
        private String content;
        private Double score;
        private LocalDateTime createdAt;
    }
}
