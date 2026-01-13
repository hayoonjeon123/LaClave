package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ItemRepository itemRepository;

    public List<Item> getItemsByCategory(Long commonIdx) {
        return itemRepository.findByCategory_commonIdx(commonIdx);
    }
}
