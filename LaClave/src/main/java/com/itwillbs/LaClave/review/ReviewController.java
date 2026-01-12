package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
	
	private final ReviewService reviewService;
	//http://localhost:8080/api/review/{memberIdx}
	// 회원이 쓴 리뷰 목록
	@GetMapping("/{memberIdx}")
	public ResponseEntity<List<Review>> getReviewBymember(
			@PathVariable("memberIdx") Integer memberIdx){
		return ResponseEntity.ok(reviewService.getReviewBymember(memberIdx));
	}
	
	//http://localhost:8080/api/review/product/{productIdx}
	//상품별 리뷰 목록
	@GetMapping("/product/{productIdx}")
	public ResponseEntity<List<Review>> getReviewByProduct(
			@PathVariable("productIdx") Integer productIdx,
			@RequestParam(value = "status", required = false, defaultValue = "ACTIVE") String status){
		return ResponseEntity.ok(reviewService.getReviewByProduct(productIdx, status));
	}
	
	//http://localhost:8080/api/review/average/{productIdx}
	// 상품별 리뷰 평점 조회
    @GetMapping("/average/{productIdx}")
    public ResponseEntity<Double> getProductAverage(@PathVariable("productIdx") Integer productIdx) {
        Double avgScore = reviewService.getProductAverageScore(productIdx);
        return ResponseEntity.ok(avgScore);
    }
	
		
	
	

}
