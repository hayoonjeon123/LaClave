package com.itwillbs.LaClave.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class CategoryController {
	private final ItemService itemService;

	public CategoryController(ItemService itemService) {
		this.itemService = itemService;
	}

	// 카테고리 조회
	@GetMapping("/category/{categoryIdx}")
	public List<CategoryResponseDto> getCategoryItems(@PathVariable("categoryIdx") Long categoryIdx) {
		return itemService.getItems(categoryIdx);
	}

	// 상품 상세 조회
	@GetMapping("/product/{productIdx}")
	public CategoryResponseDto getProductDetail(@PathVariable("productIdx") Long productIdx) {
		return itemService.getItem(productIdx);
	}

	// 베스트 상품 조회
	@GetMapping("/products/best")
	public List<CategoryResponseDto> getBestProducts() {
		return itemService.getBestProducts();
	}

	// 리뷰 조회
	@GetMapping("/items/{itemIdx}/reviews")
	public ResponseEntity<CategoryProductReviewResponse> getItemReviews(@PathVariable("itemIdx") Integer itemIdx) {
		return ResponseEntity.ok(itemService.getProductReviewData(itemIdx));
	}

}
