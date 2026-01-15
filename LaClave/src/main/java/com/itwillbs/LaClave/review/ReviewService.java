package com.itwillbs.LaClave.review;

import java.util.List;

public interface ReviewService {
	
	List<Review> getReviewBymember(Integer memberIdx);
	//상품 별 리뷰 목록 조회
	List<Review> getReviewByProduct(Integer productIdx,String status);
	
	//상품 정보와 리뷰 조회
	ReviewResponseDTO getProductWithReviews(Long productIdx);
	
	
	Double getProductAverageScore(Integer productIdx);
	

}
