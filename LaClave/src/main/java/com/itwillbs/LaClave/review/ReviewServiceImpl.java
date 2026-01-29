package com.itwillbs.LaClave.review;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.LaClave.category.Category;
import com.itwillbs.LaClave.category.Item;
import com.itwillbs.LaClave.category.ItemRepository;
import com.itwillbs.LaClave.category.ProductOption;
import com.itwillbs.LaClave.commoncode.CommonCodeService;
import com.itwillbs.LaClave.config.CustomUserDetails;
import com.itwillbs.LaClave.image.Image;
import com.itwillbs.LaClave.image.ImageRepository;
import com.itwillbs.LaClave.image.ImageUploadService;
import com.itwillbs.LaClave.inquiry.InquiryCreateRequest;
import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.member.MemberRepository;
import com.itwillbs.LaClave.orders.OrdersDetail;
import com.itwillbs.LaClave.orders.OrdersDetailRepository;
import com.itwillbs.LaClave.orders.OrdersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final ItemRepository itemRepository;
	private final OrdersDetailRepository ordersDetailRepository;
	private final MemberRepository memberRepository;
	private final OrdersRepository ordersRepository;
	private final ImageRepository imageRepository;
	private final CommonCodeService commonCodeService;
	private final ImageUploadService imageUploadService;
	
	
	//상품별 리뷰 조회
	public List<Review> getReviewByProduct(Long productIdx, String status) {
		return reviewRepository.findByProduct_ProductIdxAndStatus(productIdx, status);

	}
	//상품별 리뷰 평점 가져오기
	public Double getProductAverageScore(Long productIdx) {
		Double avg = reviewRepository.getAverageScoreByProduct(productIdx);
		return avg != null ? avg : 0.0; // 리뷰 평점이 없으면 0.0 반환
	}
	
	
	// 내가쓴 리뷰 조회
	@Override
	@Transactional(readOnly = true)
	public List<MyReviewResponseDTO> getMyReviews(Long memberIdx) {

	    List<Review> reviews =
	        reviewRepository.findAllByMember_MemberIdxAndStatus(memberIdx, "ACTIVE");

	    return reviews.stream().map(review -> {

	        Item item = review.getProduct();
	        
	        

	        // 1️⃣ 주문 옵션 정보
	        OrdersDetail detail = ordersDetailRepository
	            .findByOrdersIdxAndProductIdx(
	                review.getOrdersIdx().longValue(),
	                item.getProductIdx()
	            )
	            .orElse(null);

	        String optionInfo = "옵션 정보 없음";
	        if (detail != null) {
	            optionInfo = "색상 : " +
	                commonCodeService.getLabel(detail.getColorCode()) +
	                " / 사이즈 : " +
	                commonCodeService.getLabel(detail.getSizeCode());
	        }

	        // 상품 대표 이미지
	        String productImageUrl = !item.getImages().isEmpty() ? 
	            item.getImages().iterator().next().getUrl() : "default_image_url";

	        // 리뷰 이미지 이름만 가져오기
	        String reviewImageName = imageRepository
	            .findFirstByTargetCodeAndTargetTypeAndTargetIdx("img_04","REVIEW", review.getReviewIdx())
	            .map(Image::getImageUrl)
	            .orElse(null);


	        // 4️⃣ DTO 반환
	        return MyReviewResponseDTO.builder()
	            .reviewIdx(review.getReviewIdx())
	            .productIdx(item.getProductIdx())
	            .productName(item.getProductName())
	            .imageUrl(productImageUrl)   // 상품 대표 이미지
	            .reviewImageUrl(reviewImageName) // ✅ 여기에 리뷰 이미지 넣기
	            .optionInfo(optionInfo)
	            .content(review.getContent())
	            .score(review.getScore())
	            .ordersIdx(review.getOrdersIdx())
	            .createdAt(review.getCreatedAt()) // 리뷰 작성일
	            .build();

	    }).collect(Collectors.toList());
	}
	
	//작성가능 리뷰조회
	@Override
	public List<ReviewWritaResponseDto> getWritableReviews(Long memberIdx) {
	    List<OrdersDetail> orderDetails =
	        ordersRepository.findUnreviewedDetailsByMember(memberIdx);

	    return orderDetails.stream()
	    	    .map(detail -> {

	    	        Long productIdx = detail.getProduct().getProductIdx();

	    	        String productImageUrl = imageRepository
	    	            .findFirstByTargetCodeAndTargetTypeAndTargetIdx(
	    	                "img_01",
	    	                "PRODUCT",
	    	                productIdx.intValue()
	    	            )
	    	            .map(Image::getImageUrl)
	    	            .orElse("default_image_url");

	    	        return ReviewWritaResponseDto.builder()
	    	            .reviewIdx(0L)
	    	            .productIdx(productIdx)
	    	            .productName(detail.getProduct().getProductName())
	    	            .productImageUrl(productImageUrl)   // ⭐⭐⭐ 여기!!!
	    	            .optionInfo("색상: " + detail.getColorCode()
	    	                       + ", 사이즈: " + detail.getSizeCode())
	    	            .content("")
	    	            .score(0)
	    	            .ordersIdx(detail.getOrder().getOrdersIdx())
	    	            .build();
	    	    })
	    	    .collect(Collectors.toList());
	}

	
	
	

	//리뷰 작성
	@Override
	@Transactional
	public void createReview(CustomUserDetails user, ReviewCreateRequest dto, MultipartFile image) {

	    // 1️⃣ 상품 조회
	    Item product = itemRepository.findById(dto.getProductIdx())
	        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

	    // 2️⃣ 중복 리뷰 체크
	    boolean exists = reviewRepository
	        .existsByMember_MemberIdxAndOrdersIdxAndProduct_ProductIdx(
	            user.getMemberIdx(),
	            dto.getOrdersIdx(),
	            product.getProductIdx()
	        );

	    if (exists) {
	        throw new IllegalStateException("이미 리뷰를 작성한 상품입니다.");
	    }

	    // 3️⃣ 리뷰 생성
	    Review review = Review.create(
	        user.getMember(),
	        product,
	        dto.getOrdersIdx(),
	        dto.getScore(),
	        dto.getContent()
	    );

	    reviewRepository.save(review);
	    
	    // 4️⃣ 이미지가 있을 경우만 저장
	    if (image != null && !image.isEmpty()) {

	        // 👉 실제로는 S3 / 로컬 업로드 서비스 분리 추천
	        String imageUrl = imageUploadService.upload(image);

	        Image reviewImage = Image.builder()
	        	    .targetCode("img_04")          // 무엇에 대한 이미지인지
	        	    .targetType("REVIEW")          // 용도 (리뷰 이미지)
	        	    .targetIdx(review.getReviewIdx())
	        	    .imageUrl(imageUrl)
	        	    .build();

	        imageRepository.save(reviewImage);
	    }

	}
	
	//리뷰수정
	@Transactional
	public void updateReview(
	        CustomUserDetails user,
	        Integer reviewIdx,
	        ReviewUpdateRequest request,
	        MultipartFile image
	) {
	    Review review = reviewRepository.findById(reviewIdx)
	            .orElseThrow(() -> new IllegalArgumentException("리뷰 없음"));

	    // 🔒 작성자 검증
	    if (!review.getMember().getMemberIdx().equals(user.getMemberIdx())) {
	        throw new IllegalStateException("수정 권한이 없습니다.");
	    }

	    // 1️⃣ 리뷰 내용 업데이트
	    review.update(request.getScore(), request.getContent());
	    review.setUpdatedAt(LocalDateTime.now());

	    // 2️⃣ 이미지가 새로 들어온 경우 처리
	    if (image != null && !image.isEmpty()) {

	        // 기존 이미지 삭제 (DB만 삭제, 로컬/서버 파일 삭제도 가능)
	        imageRepository.findFirstByTargetCodeAndTargetTypeAndTargetIdx(
	        		"img_04", "REVIEW", review.getReviewIdx()
	        ).ifPresent(oldImage -> {
	            imageRepository.delete(oldImage);

	            // 로컬 파일도 삭제하고 싶으면 주석 해제
	            // File file = new File("C:/upload2/review/" + Paths.get(oldImage.getImageUrl()).getFileName());
	            // if (file.exists()) file.delete();
	        });

	        // 새 이미지 업로드
	        String imageUrl = imageUploadService.upload(image);

	        Image reviewImage = Image.builder()
	                .targetCode("img_04")
	                .targetType("REVIEW")
	                .targetIdx(review.getReviewIdx())
	                .imageUrl(imageUrl)
	                .build();

	        imageRepository.save(reviewImage);
	    }
	}
	
	
	
	// 리뷰 삭제
	@Override
	@Transactional
	public void deleteReview(CustomUserDetails user, Integer reviewIdx) {

	    Review review = reviewRepository.findById(reviewIdx)
	            .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

	    // 🔒 본인 리뷰인지 검증 (정석)
	    if (!review.getMember().getMemberIdx().equals(user.getMemberIdx())) {
	        throw new IllegalStateException("삭제 권한이 없습니다.");
	    }
	    
	    review.delete();


	    log.info("리뷰 삭제 완료 - reviewIdx: {}, memberIdx: {}", 
	             reviewIdx, user.getMemberIdx());
	}
}
