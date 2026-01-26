package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Cart.ItemImage;
import com.itwillbs.LaClave.review.Review;
import com.itwillbs.LaClave.review.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemService {

	private final ItemRepository itemRepository;

	private final ReviewRepository reviewRepository;

	private final com.itwillbs.LaClave.wishlist.WishlistRepository wishlistRepository;

	// 더미 넣고 확인
	// 카테고리 번호 상품 조회
	// public List<CategoryResponseDto> getItemsByProductCategoryIdx(Long
	// productCategoryIdx) {
	// List<Item> items =
	// itemRepository.findByProductCategoryIdx(productCategoryIdx);
	// return
	// items.stream().map(CategoryResponseDto::new).collect(Collectors.toList());
	// }

	// pk에 따라 상품 목록 조회
	public List<CategoryResponseDto> getItems(Long productCommonIdx) {
		List<Item> items;
		if (productCommonIdx < 100) {
			items = itemRepository.findByProductCommonIdx(productCommonIdx);
		} else {
			items = itemRepository.findByProductSubcategoryIdx(productCommonIdx);
		}

		if (items.isEmpty())
			return new ArrayList<>();

		List<CategoryResponseDto> responseList = new ArrayList<>();
		for (Item item : items) {

			if (item.getImages() != null) {
				item.getImages().size();
			}
			if (item.getOptions() != null) {
				item.getOptions().size();
			}
			responseList.add(new CategoryResponseDto(item));
		}

		return responseList;
	}

	// 상품 상세 정보 조회
	@Transactional
	public CategoryResponseDto getItem(Long productIdx) {
		log.info("ItemService - getItem 호출: {}", productIdx);
		Item item = itemRepository.findById(productIdx)
				.orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. (ID: " + productIdx + ")"));

		log.info("ItemService - 상품 조회 성공: {}, 옵션 개수: {}", item.getProductName(),
				item.getOptions() != null ? item.getOptions().size() : 0);

		CategoryResponseDto dto = new CategoryResponseDto(item);

		// 리뷰 정보
		Double averageRating = reviewRepository.getAverageScoreByProduct(productIdx);
		Integer reviewCount = reviewRepository.countByProductIdx(productIdx.intValue());

		dto.setAverageRating(averageRating != null ? averageRating : 0.0);
		dto.setReviewCount(reviewCount != null ? reviewCount : 0);

		// 찜 개수
		Integer wishlistCount = wishlistRepository.countByProductIdx(productIdx.intValue());
		dto.setWishlistCount(wishlistCount != null ? wishlistCount : 0);

		log.info("ItemService - DTO 변환 완료: {}, 평균평점: {}, 리뷰수: {}, 찜수: {}",
				dto.getProductName(), dto.getAverageRating(), dto.getReviewCount(), dto.getWishlistCount());

		return dto;
	}

	// 베스트 상품 조회 (현재 모든 상품, 나중 추가)
	@Transactional
	public List<CategoryResponseDto> getBestProducts() {
		log.info("베스트 상품 조회");

		// 모든 상품 조회
		List<Item> items = itemRepository.findAll();

		if (items.isEmpty()) {
			return new ArrayList<>();
		}

		List<CategoryResponseDto> responseList = new ArrayList<>();
		for (Item item : items) {

			if (item.getImages() != null) {
				item.getImages().size();
			}
			if (item.getOptions() != null) {
				item.getOptions().size();
			}
			responseList.add(new CategoryResponseDto(item));
		}

		log.info("베스트 상품 조회 완료: {} 개", responseList.size());
		return responseList;
	}

	// 상품 상세페이지 리뷰
	public CategoryProductReviewResponse getProductReviewData(Integer productIdx) {
		Double avgScore = reviewRepository.getAverageScoreByProduct(productIdx.longValue());
		if (avgScore == null)
			avgScore = 0.0;

		List<Review> reviewEntities = reviewRepository.findByProductIdx(productIdx);

		List<CategoryProductReviewResponse.ReviewDetail> details = reviewEntities.stream()
				.map(CategoryProductReviewResponse.ReviewDetail::new)
				.collect(Collectors.toList());

		return new CategoryProductReviewResponse(avgScore, details);
	}

	// 평균 점수
	public Double getAverageScore(Long productIdx) {
		return reviewRepository.getAverageScoreByProduct(productIdx);
	}
}