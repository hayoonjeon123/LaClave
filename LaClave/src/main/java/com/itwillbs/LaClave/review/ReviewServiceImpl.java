package com.itwillbs.LaClave.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Category.Category; 
import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Category.ProductOption;
import com.itwillbs.LaClave.Orders.OrdersDetail;
import com.itwillbs.LaClave.Orders.OrdersDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewRepository reviewRepository;
	private final ItemRepository itemRepository;
	private final OrdersDetailRepository ordersDetailRepository ;
	

	
	public List<Review> getReviewByProduct(Long productIdx,String status) {
		return reviewRepository.findByProductIdxAndStatus(productIdx, status);
		
	}
	
	public Double getProductAverageScore(Long productIdx) {
		 Double avg = reviewRepository.getAverageScoreByProduct(productIdx);
		 return avg != null ? avg : 0.0; //리뷰 평점이 없으면 0.0 반환
	}
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
	            OrdersDetail detail = ordersDetailRepository.findByOrdersIdxAndProductIdx(
	                    review.getOrdersIdx().longValue(),
	                    review.getProductIdx().longValue()
	            ).orElse(null);

	            // 4. 옵션 정보 문자열 생성
	            String optionInfo = "옵션 정보 없음";
	            if (detail != null) {
	                // 숫자 코드를 한글로 바꾸는 로직이 따로 없다면 우선 숫자로 표시
	                optionInfo = "Color: " + detail.getColorCode() + " / Size: " + detail.getSizeCode();
	            }

	            // 5. 이미지 URL 추출 (첫 번째 이미지)
	            String imageUrl = (item != null && !item.getImages().isEmpty())
	                    ? item.getImages().iterator().next().getImagePath()
	                    : "default_image_url"; // 기본 이미지 경로

	            // 6. DTO 조립
	            return MyReviewResponseDTO.builder()
	                    .reviewIdx(review.getReviewIdx())
	                    .content(review.getContent())
	                    .score(review.getScore())
	                    .createdAt(review.getCreatedAt())
	                    .productIdx(review.getProductIdx().longValue())
	                    .productName(item != null ? item.getProductName() : "삭제된 상품")
	                    .imageUrl(imageUrl)
	                    .optionInfo(optionInfo)
	                    .ordersIdx(review.getOrdersIdx())
	                    .build();
	        }).collect(Collectors.toList());
	    }

}
