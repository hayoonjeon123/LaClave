package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewRepository reviewRepository;
	
	@Override
	public List<Review> getReviewBymember(Integer memberIdx) {
		return reviewRepository.findByMemberIdx(memberIdx);
		
	}
	
	public List<Review> getReviewByProduct(Integer productIdx,String status) {
		return reviewRepository.findByProductIdxAndStatus(productIdx, status);
		
	}
	
	public Double getProductAverageScore(Integer productIdx) {
		 Double avg = reviewRepository.getAverageScoreByProduct(productIdx);
		 return avg != null ? avg : 0.0; //리뷰 평점이 없으면 0.0 반환
	}

}
