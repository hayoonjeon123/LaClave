package com.itwillbs.LaClave.Category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Service
@RequiredArgsConstructor
@Log4j2
public class ItemService {
	
    private final ItemRepository itemRepository;

    public List<CategoryResponseDto> getItemsByProductCategoryIdx(Long productCategoryIdx) {
    	List<Item> items = itemRepository.findByProductCategoryIdx(productCategoryIdx);
        return items.stream()
                    .map(CategoryResponseDto::new)
                    .collect(Collectors.toList());
    }
    
    public List<CategoryResponseDto> getItems(Long productCommonIdx) {
        List<Item> items;
        
        // 사용자님의 CLASS_CATEGORY 구조상 100 미만(86, 87 등)은 상위 카테고리입니다.
        if (productCommonIdx < 100) { 
            // 86번이 들어오면 상의 전체를 찾음
            items = itemRepository.findByProductCommonIdx(productCommonIdx); 
        } else {
            // 108번이 들어오면 해당 하위 카테고리만 찾음
            items = itemRepository.findByProductCategoryIdx(productCommonIdx); 
        }
        
        return items.stream()
                    .map(CategoryResponseDto::new)
                    .toList();
    }
}

