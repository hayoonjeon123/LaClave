package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ItemRepository itemRepository;

    public List<Item> getItemsByCategory(Long commonIdx) {
        // 리포지토리를 통해 하위 카테고리 코드와 일치하는 상품 리스트를 가져옵니다.
        return itemRepository.findByCategory_commonIdx(commonIdx);
    }
}
