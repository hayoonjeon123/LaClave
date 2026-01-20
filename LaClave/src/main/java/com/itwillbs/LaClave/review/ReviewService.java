package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.LaClave.security.CustomUserDetails;

public interface ReviewService {

//	List<Review> getReviewBymember(Long memberIdx);
	// 상품 별 리뷰 목록 조회
	List<Review> getReviewByProduct(Long productIdx, String status);

	// 상품 정보와 리뷰 조회
	List<MyReviewResponseDTO> getMyReviews(Long productIdx);
	
	public List<ReviewResponseDto> getWritableReviews(Long memberIdx);

	Double getProductAverageScore(Long productIdx);

	void createReview(CustomUserDetails user, ReviewCreateRequest dto, MultipartFile image);

	void updateReview(CustomUserDetails user, Integer reviewIdx, ReviewUpdateRequest request, MultipartFile image);

	void deleteReview(CustomUserDetails user, Integer reviewIdx);

}
