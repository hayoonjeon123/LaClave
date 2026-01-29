package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.LaClave.config.CustomUserDetails;

public interface ReviewService {

    // 상품별 리뷰 목록
    List<Review> getReviewByProduct(Long productIdx, String status);

    // ✅ 내가 쓴 리뷰 목록 (member 기준)
    List<MyReviewResponseDTO> getMyReviews(Long memberIdx);

    // 작성 가능한 리뷰
    List<ReviewWritaResponseDto> getWritableReviews(Long memberIdx);

    // 상품별 평균 별점
    Double getProductAverageScore(Long productIdx);

    // 리뷰 작성
    void createReview(CustomUserDetails user, ReviewCreateRequest dto, MultipartFile image);

    // 리뷰 수정
    void updateReview(CustomUserDetails user, Integer reviewIdx, ReviewUpdateRequest request, MultipartFile image);

    // 리뷰 삭제
    void deleteReview(CustomUserDetails user, Integer reviewIdx);
}
