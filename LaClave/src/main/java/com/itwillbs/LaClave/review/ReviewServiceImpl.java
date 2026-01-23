//package com.itwillbs.LaClave.review;
//
//import java.nio.file.AccessDeniedException;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.itwillbs.LaClave.Category.Category;
//import com.itwillbs.LaClave.Category.Item;
//import com.itwillbs.LaClave.Category.ItemRepository;
//import com.itwillbs.LaClave.Category.ProductOption;
//import com.itwillbs.LaClave.Member.Member;
//import com.itwillbs.LaClave.Member.MemberRepository;
//import com.itwillbs.LaClave.Orders.OrdersDetail;
//import com.itwillbs.LaClave.Orders.OrdersDetailRepository;
//import com.itwillbs.LaClave.Orders.OrdersRepository;
//import com.itwillbs.LaClave.commoncode.CommonCodeService;
//import com.itwillbs.LaClave.inquiry.InquiryCreateRequest;
//import com.itwillbs.LaClave.security.CustomUserDetails;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Service
//@RequiredArgsConstructor
//@Log4j2
//public class ReviewServiceImpl implements ReviewService {
//
//	private final ReviewRepository reviewRepository;
//	private final ItemRepository itemRepository;
//	private final OrdersDetailRepository ordersDetailRepository;
//	private final MemberRepository memberRepository;
//	private final OrdersRepository ordersRepository;
//	private final CommonCodeService commonCodeService;
//	
//	
//	
//	//상품별 리뷰 조회
//	public List<Review> getReviewByProduct(Long productIdx, String status) {
//		return reviewRepository.findByProductIdxAndStatus(productIdx, status);
//
//	}
//	//상품별 리뷰 평점 가져오기
//	public Double getProductAverageScore(Long productIdx) {
//		Double avg = reviewRepository.getAverageScoreByProduct(productIdx);
//		return avg != null ? avg : 0.0; // 리뷰 평점이 없으면 0.0 반환
//	}
//	
//	@Override
//	@Transactional(readOnly = true)
//	public List<MyReviewResponseDTO> getMyReviews(Long memberIdx) {
//
//	    List<Review> reviews =
//	            reviewRepository.findAllByMemberIdxAndStatus(memberIdx, "ACTIVE");
//
//	    return reviews.stream().map(review -> {
//
//	        // 1️⃣ 상품 정보
//	        Item item = itemRepository
//	                .findById(review.getProductIdx().longValue())
//	                .orElse(null);
//
//	        // 2️⃣ 주문 상세 (옵션 코드용)
//	        OrdersDetail detail = ordersDetailRepository
//	                .findByOrdersIdxAndProductIdx(
//	                        review.getOrdersIdx().longValue(),
//	                        review.getProductIdx().longValue()
//	                )
//	                .orElse(null);
//
//	        // 3️⃣ 옵션 정보 생성 ✅ 여기!!
//	        String optionInfo = "옵션 정보 없음";
//	        if (detail != null) {
//	            String colorName = commonCodeService.getLabel(detail.getColorCode());
//	            String sizeName  = commonCodeService.getLabel(detail.getSizeCode());
//
//	            optionInfo = "색상 : " + colorName + " / 사이즈 : " + sizeName;
//	        }
//
//	        // 4️⃣ 이미지
//	        String imageUrl = (item != null && !item.getImages().isEmpty())
//	                ? item.getImages().iterator().next().getUrl()
//	                : "default_image_url";
//
//	        // 5️⃣ DTO 조립
//	        return MyReviewResponseDTO.builder()
//	                .reviewIdx(review.getReviewIdx())
//	                .productIdx(review.getProductIdx().longValue())
//	                .productName(item != null ? item.getProductName() : "삭제된 상품")
//	                .imageUrl(imageUrl)
//	                .optionInfo(optionInfo)   // ✅ 변환된 문자열
//	                .content(review.getContent())
//	                .score(review.getScore())
//	                .ordersIdx(review.getOrdersIdx())
//	                .build();
//
//	    }).collect(Collectors.toList());
//	}
////	// 작성 가능 리뷰
////	@Override
////	public List<reviewResponseDto> getWritableReviews(Long memberIdx) {
////	    List<OrdersDetail> orderDetails = ordersRepository.findUnreviewedDetailsByMember(memberIdx);
////
////	    return orderDetails.stream()
////	        .map(detail -> new ReviewResponseDto(
////	                0L, // reviewIdx 없음
////	                detail.getProduct().getProductIdx(),
////	                detail.getProduct().getProductName(),
////	                "",
////	                "색상: " + detail.getColorCode() + ", 사이즈: " + detail.getSizeCode(),
////	                "",
////	                0,
////	                detail.getOrder().getOrdersIdx()
////	        ))
////	        .collect(Collectors.toList());
////	}
////	
//
//	//리뷰 작성
//	@Override
//	@Transactional
//	public void createReview(CustomUserDetails user, ReviewCreateRequest dto, MultipartFile image) {
//
//	    boolean exists = reviewRepository
//	        .existsByMember_MemberIdxAndOrdersIdxAndProductIdx(
//	            user.getMemberIdx(),
//	            dto.getOrdersIdx(),
//	            dto.getProductIdx()
//	        );
//
//	    if (exists) {
//	        throw new IllegalStateException("이미 리뷰를 작성한 상품입니다.");
//	    }
//	    
//	    
//
//	    Review review = Review.create(
//	        user.getMember(),          
//	        dto.getProductIdx(),
//	        dto.getOrdersIdx(),
//	        dto.getScore(),
//	        dto.getContent()
//	    );
//
//	    reviewRepository.save(review);
//	}
//	
//	
//	//리뷰 수정
//	@Transactional
//	public void updateReview(
//	        CustomUserDetails user,
//	        Integer reviewIdx,
//	        ReviewUpdateRequest request,
//	        MultipartFile image
//	) {
//	    Review review = reviewRepository.findById(reviewIdx)
//	            .orElseThrow(() -> new IllegalArgumentException("리뷰 없음"));
//
//	    // 🔒 작성자 검증 (member 기준으로 통일)
//	    if (!review.getMember().getMemberIdx().equals(user.getMemberIdx())) {
//	        throw new IllegalStateException("수정 권한이 없습니다.");
//	    }
//
//	    review.update(request.getScore(), request.getContent());
//	    review.setUpdatedAt(LocalDateTime.now());
////
////	    if (image != null && !image.isEmpty()) {
////	        String imageUrl = upload(image); // 구현 필요
////	        review.updateImage(imageUrl);
////	    }
//	}
//	
//	
//	// 리뷰 삭제
//	@Override
//	@Transactional
//	public void deleteReview(CustomUserDetails user, Integer reviewIdx) {
//
//	    Review review = reviewRepository.findById(reviewIdx)
//	            .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
//
//	    // 🔒 본인 리뷰인지 검증 (정석)
//	    if (!review.getMember().getMemberIdx().equals(user.getMemberIdx())) {
//	        throw new IllegalStateException("삭제 권한이 없습니다.");
//	    }
//	    
//	    review.delete();
//
//
//	    log.info("리뷰 삭제 완료 - reviewIdx: {}, memberIdx: {}", 
//	             reviewIdx, user.getMemberIdx());
//	}
//}
