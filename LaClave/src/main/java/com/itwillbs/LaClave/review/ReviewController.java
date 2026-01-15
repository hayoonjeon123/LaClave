package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
	//http://localhost:8080/api/review
	// 회원이 쓴 리뷰 목록
    @GetMapping("/my")
    public ResponseEntity<List<Review>> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
    	log.info("user: {}" ,user);
    	Long memberIdx = user.getMemberIdx();
        return ResponseEntity.ok(reviewService.getReviewBymember(memberIdx));
    }
    
	//http://localhost:8080/api/review/product/{productIdx}
	//상품별 리뷰 목록
	@GetMapping("/product/{productIdx}")
	public ResponseEntity<List<Review>> getReviewByProduct(
			@PathVariable("productIdx") Long productIdx,
			@RequestParam(value = "status", required = false, defaultValue = "ACTIVE") String status){
		return ResponseEntity.ok(reviewService.getReviewByProduct(productIdx, status));
	}
	
	//http://localhost:8080/api/review/average/{productIdx}
	// 상품별 리뷰 평점 조회
    @GetMapping("/average/{productIdx}")
    public ResponseEntity<Double> getProductAverage(@PathVariable("productIdx") Long productIdx) {
        Double avgScore = reviewService.getProductAverageScore(productIdx);
        return ResponseEntity.ok(avgScore);
    }

    /**
     * 특정 상품의 상세 정보 + 옵션 + 리뷰 조회
     * URL 예: /api/product/123/reviews
     */
    @GetMapping("/product/{productIdx}/reviews")
    @ResponseBody
    public ReviewResponseDTO getProductWithReviews(@PathVariable("productIdx") Long productIdx) {
        return reviewService.getProductWithReviews(productIdx);
    }
    
//    @PostMapping
//    public ResponseEntity<Review> createReview(@RequestBody Review review){
//        Review saved = reviewService.createReview(review);
//        return ResponseEntity.ok(saved);
//    }
//
//    @PutMapping
//    public ResponseEntity<Review> updateReview(@RequestBody Review review){
//        Review updated = reviewService.updateReview(review);
//        return ResponseEntity.ok(updated);
//    }
//
//    @DeleteMapping("/{reviewIdx}")
//    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewIdx){
//        reviewService.deleteReview(reviewIdx);
//        return ResponseEntity.ok().build();
//    }	
	
	

}
