package com.itwillbs.LaClave.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.LaClave.Category.Category;
import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Category.ProductOption;
import com.itwillbs.LaClave.Orders.OrdersDetail;
import com.itwillbs.LaClave.Orders.OrdersDetailRepository;
import com.itwillbs.LaClave.inquiry.InquiryCreateRequest;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final ItemRepository itemRepository;
	private final OrdersDetailRepository ordersDetailRepository;
	
	//상품별 리뷰 조회
	public List<Review> getReviewByProduct(Long productIdx, String status) {
		return reviewRepository.findByProductIdxAndStatus(productIdx, status);

	}
	//상품별 리뷰 평점 가져오기
	public Double getProductAverageScore(Long productIdx) {
		Double avg = reviewRepository.getAverageScoreByProduct(productIdx);
		return avg != null ? avg : 0.0; // 리뷰 평점이 없으면 0.0 반환
	}
	
	
	//리뷰 조회
	@Override
	@Transactional(readOnly = true)
	public List<MyReviewResponseDTO> getMyReviews(Long memberIdx) {
		// 1. 해당 회원의 리뷰 목록 조회
		List<Review> reviews = reviewRepository.findAllByMemberIdx(memberIdx);

		return reviews.stream().map(review -> {
			// 2. 상품 정보 조회 (상품명, 이미지용)
			Item item = itemRepository.findById(review.getProductIdx().longValue()).orElse(null);

			// 3. 주문 상세 정보 조회 (구매한 옵션 코드용)
			// findByOrder_OrdersIdxAndProductIdx 같은 메서드가 Repository에 필요합니다.
			OrdersDetail detail = ordersDetailRepository
					.findByOrdersIdxAndProductIdx(review.getOrdersIdx().longValue(), review.getProductIdx().longValue())
					.orElse(null);

			// 4. 옵션 정보 문자열 생성
			String optionInfo = "옵션 정보 없음";
			if (detail != null) {
				// 숫자 코드를 한글로 바꾸는 로직이 따로 없다면 우선 숫자로 표시
				optionInfo = "Color: " + detail.getColorCode() + " / Size: " + detail.getSizeCode();
			}

			// 5. 이미지 URL 추출 (첫 번째 이미지)
			String imageUrl = (item != null && !item.getImages().isEmpty())
					? item.getImages().iterator().next().getUrl()
					: "default_image_url"; // 기본 이미지 경로

			// 6. DTO 조립
			return MyReviewResponseDTO.builder().reviewIdx(review.getReviewIdx()).content(review.getContent())
					.score(review.getScore()).createdAt(review.getCreatedAt())
					.productIdx(review.getProductIdx().longValue())
					.productName(item != null ? item.getProductName() : "삭제된 상품").imageUrl(imageUrl)
					.optionInfo(optionInfo).ordersIdx(review.getOrdersIdx()).build();
		}).collect(Collectors.toList());
	}

	
	//리뷰 작성
	@Override
	public void createReview(CustomUserDetails user, ReviewCreateRequest dto, MultipartFile image) {
		Review review = Review.create(user.getMemberIdx(), dto.getProductIdx(), dto.getOrdersIdx(), dto.getScore(),
				dto.getContent());
		reviewRepository.save(review);
	}

	@Transactional
	@Override
	public void updateReview(CustomUserDetails user, Integer reviewIdx, ReviewUpdateRequest request) {

		Review review = reviewRepository.findById(reviewIdx)
				.orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

		// ✨ 본인 리뷰만 수정 가능
		if (!review.getMemberIdx().equals(user.getMemberIdx().intValue())) {
			throw new IllegalStateException("수정 권한이 없습니다.");
		}

		// ✨ 삭제된 리뷰 수정 방지
		if ("DELETED".equals(review.getStatus())) {
			throw new IllegalStateException("삭제된 리뷰는 수정할 수 없습니다.");
		}

		review.setScore(request.getScore());
		review.setContent(request.getContent());
		review.setUpdatedAt(LocalDateTime.now());
	}
	// 리뷰 삭제
	@Override
	@Transactional
	public void deleteReview(CustomUserDetails user, Integer reviewIdx) {
		Review review = reviewRepository.findById(reviewIdx)
				.orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

		log.info("삭제 시도 - 리뷰 작성자 memberIdx: {}, 로그인 사용자 memberIdx: {}", review.getMemberIdx(), user.getMemberIdx());

		log.info("review.getMemberIdx = {}, class = {}", review.getMemberIdx(), review.getMemberIdx().getClass());
		log.info("user.getMemberIdx = {}, class = {}", user.getMemberIdx(), user.getMemberIdx().getClass());

		if (!Objects.equals(review.getMemberIdx(), user.getMemberIdx())) {
			throw new IllegalStateException("삭제 권한이 없습니다.");
		}
		review.setStatus("DELETED");
		review.setUpdatedAt(LocalDateTime.now());

		log.info("리뷰 삭제 완료 - reviewIdx: {}, memberIdx: {}", reviewIdx, user.getMemberIdx());
	}
}
