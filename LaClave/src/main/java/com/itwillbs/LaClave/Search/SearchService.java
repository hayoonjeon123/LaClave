package com.itwillbs.LaClave.Search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemRepository itemRepository;

    public List<Item> searchItems(ItemSearchCondition condition) {
        // 상품명, 스타일태그, 카테고리(대/소분류 모두) 통합 검색 수행
        return itemRepository.searchByKeyword(condition.getKeyword());
    }
}
