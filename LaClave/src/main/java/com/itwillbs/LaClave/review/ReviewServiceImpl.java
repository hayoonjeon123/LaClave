package com.itwillbs.LaClave.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.itwillbs.LaClave.Category.Category; 
import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Category.ProductOption;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewRepository reviewRepository;
	private final ItemRepository itemRepository;
	
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
	 @Override
	    public ReviewResponseDTO getProductWithReviews(Long productIdx) {

	        // 상품 조회
	        Item item = itemRepository.findById(productIdx)
	                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

	        // 옵션 이름만 추출 (ProductOption.optionName 가 있다고 가정)
	        List<String> options = item.getOptions().stream()
	                .map((ProductOption opt) -> {
	                    // 만약 ProductOption에 이름 필드 없으면 컬러+사이즈 합쳐서 문자열 생성 가능
	                    String color = opt.getColorCategory() != null ? opt.getColorCategory().getCodeDesc() : "";
	                    String size = opt.getSizeCategory() != null ? opt.getSizeCategory().getCodeDesc() : "";
	                    return (color + " " + size).trim();
	                })
	                .distinct()
	                .collect(Collectors.toList());

	        // 리뷰 조회
	        List<Review> reviews = reviewRepository.findByProductIdx(productIdx.intValue());
	        List<ReviewResponseDTO.ReviewDTO> reviewDTOs = reviews.stream()
	        	    .map(r -> new ReviewResponseDTO.ReviewDTO(
	        	            r.getReviewIdx(),
	        	            r.getMemberIdx(),
	        	            r.getContent(),
	        	            r.getScore(),
	        	            r.getCreatedAt()
	        	    ))
	        	    .collect(Collectors.toList());

	        return new ReviewResponseDTO(
	                item.getProductIdx(),
	                item.getProductName(),
	                options,
	                reviewDTOs
	        );
	    }

}
