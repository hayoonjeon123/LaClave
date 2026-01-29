package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.LaClave.cart.CartRequestDto;
import com.itwillbs.LaClave.config.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

	private final ReviewService reviewService;

	/**
	 * [마이페이지] 회원이 작성한 리뷰 목록 조회 반환 타입을 List<Review>에서 List<MyReviewResponseDTO>로
	 * 변경합니다.
	 */
	@GetMapping("/my")
	public ResponseEntity<List<MyReviewResponseDTO>> getMyReviews(@AuthenticationPrincipal CustomUserDetails user) {
		if (user == null) {
			return ResponseEntity.status(401).build();
		}

		log.info("로그인한 회원 IDX: {}", user.getMemberIdx());

		// CustomUserDetails의 memberIdx가 Long이라면 intValue()로 변환 (Review 엔티티가 Integer인
		// 경우)
		Long memberIdx = (long) user.getMemberIdx().intValue();

		// 서비스에서 가공된 DTO 리스트를 가져옵니다.
		List<MyReviewResponseDTO> myReviews = reviewService.getMyReviews(memberIdx);

		return ResponseEntity.ok(myReviews);
	}

	
    // 작성 가능 리뷰 목록 조회
    @GetMapping("/writable")
    public ResponseEntity<List<ReviewWritaResponseDto>> getWritableReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberIdx = userDetails.getMember().getMemberIdx();

        List<ReviewWritaResponseDto> result =
                reviewService.getWritableReviews(memberIdx);

        return ResponseEntity.ok(result);
    }
	

	// http://localhost:8080/api/review/product/{productIdx}
	// 상품별 리뷰 목록
	@GetMapping("/product/{productIdx}")
	public ResponseEntity<List<Review>> getReviewByProduct(@PathVariable("productIdx") Long productIdx,
			@RequestParam(value = "status", required = false, defaultValue = "ACTIVE") String status) {
		return ResponseEntity.ok(reviewService.getReviewByProduct(productIdx, status));
	}

	// http://localhost:8080/api/review/average/{productIdx}
	// 상품별 리뷰 평점 조회
	@GetMapping("/average/{productIdx}")
	public ResponseEntity<Double> getProductAverage(@PathVariable("productIdx") Long productIdx) {
		Double avgScore = reviewService.getProductAverageScore(productIdx);
		return ResponseEntity.ok(avgScore);
	}

	/**
	 * 특정 상품의 상세 정보 + 옵션 + 리뷰 조회 URL 예: /api/product/123/reviews
	 */
//    @GetMapping("/product/{productIdx}/reviews")
//    @ResponseBody
//    public ReviewResponseDTO getProductWithReviews(@PathVariable("productIdx") Long productIdx) {
//        return reviewService.getProductWithReviews(productIdx);
//    }
//   
	// 리뷰 작성하기
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createReview(
	        @AuthenticationPrincipal CustomUserDetails user,

	        @Valid @RequestPart("review") ReviewCreateRequest dto,
	        @RequestPart(value = "image", required = false) MultipartFile image) {

	    reviewService.createReview(user, dto, image);
	    log.info("로그인 사용자 memberIdx = {}", user.getMemberIdx());
	    return ResponseEntity.ok().build();
	}
	
	
	//리뷰 수정
	@PutMapping("/{reviewIdx}")
	public ResponseEntity<Void> updateReview(
	    @AuthenticationPrincipal CustomUserDetails user,
	    @PathVariable("reviewIdx") Integer reviewIdx, 
	    @RequestPart("review") ReviewUpdateRequest request,
	    @RequestPart(value = "image", required = false) MultipartFile image
	) {
	    reviewService.updateReview(user, reviewIdx, request, image);
	    return ResponseEntity.ok().build();
	}
	
	//리뷰 삭제
	@DeleteMapping("/{reviewIdx}")
	public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal CustomUserDetails user,
	        @PathVariable("reviewIdx") Integer reviewIdx) {
	    
	    reviewService.deleteReview(user, reviewIdx);
	    return ResponseEntity.ok().build();
	}
//		

}
