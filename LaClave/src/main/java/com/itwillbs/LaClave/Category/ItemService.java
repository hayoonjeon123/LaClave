package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Cart.ItemImage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemService {

	private final ItemRepository itemRepository;

	public List<CategoryResponseDto> getItemsByProductCategoryIdx(Long productCategoryIdx) {
		List<Item> items = itemRepository.findByProductCategoryIdx(productCategoryIdx);
		return items.stream().map(CategoryResponseDto::new).collect(Collectors.toList());
	}

	public List<CategoryResponseDto> getItems(Long productCommonIdx) {
		// 1. 아이템 목록 먼저 조회
		List<Item> items;
		if (productCommonIdx < 100) {
			items = itemRepository.findByProductCommonIdx(productCommonIdx);
		} else {
			items = itemRepository.findByProductCategoryIdx(productCommonIdx);
		}

		// 2. 에러 방지용: 아이템이 없으면 바로 빈 리스트 반환
		if (items.isEmpty())
			return new ArrayList<>();

		// 3. DTO로 변환 (아이템 엔티티 하나씩 처리)
		List<CategoryResponseDto> responseList = new ArrayList<>();
		for (Item item : items) {
			System.out.println(
					"=== 서비스에서 Item 처리 중: " + item.getProductName() + " (ID: " + item.getProductIdx() + ") ===");
			System.out.println("서비스 - Images 개수: " + (item.getImages() != null ? item.getImages().size() : "null"));
			System.out.println("서비스 - Options 개수: " + (item.getOptions() != null ? item.getOptions().size() : "null"));

			// 강제로 컬렉션 초기화 시도
			if (item.getImages() != null) {
				item.getImages().size(); // 강제 초기화
			}
			if (item.getOptions() != null) {
				item.getOptions().size(); // 강제 초기화
			}

			System.out.println("초기화 후 - Images 개수: " + (item.getImages() != null ? item.getImages().size() : "null"));
			System.out
					.println("초기화 후 - Options 개수: " + (item.getOptions() != null ? item.getOptions().size() : "null"));

			// 이 시점에 이미지 데이터가 BatchSize 설정으로 인해 자동으로 묶여서 들어옵니다.
			responseList.add(new CategoryResponseDto(item));
		}

		return responseList;
	}

	@Transactional
	public CategoryResponseDto getItem(Long productIdx) {
		log.info("ItemService - getItem 호출: {}", productIdx);
		Item item = itemRepository.findById(productIdx)
				.orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. (ID: " + productIdx + ")"));

		log.info("ItemService - 상품 조회 성공: {}, 옵션 개수: {}", item.getProductName(),
				item.getOptions() != null ? item.getOptions().size() : 0);

		CategoryResponseDto dto = new CategoryResponseDto(item);
		log.info("ItemService - DTO 변환 완료: {}", dto.getProductName());

		return dto;
	}

	// 베스트 상품 조회 (전체 상품 조회)
	@Transactional
	public List<CategoryResponseDto> getBestProducts() {
		log.info("베스트 상품 조회");

		// 모든 상품 조회
		List<Item> items = itemRepository.findAll();

		if (items.isEmpty()) {
			return new ArrayList<>();
		}

		// DTO로 변환
		List<CategoryResponseDto> responseList = new ArrayList<>();
		for (Item item : items) {
			// 강제로 컬렉션 초기화
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
}