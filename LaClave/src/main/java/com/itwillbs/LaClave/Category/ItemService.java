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

    public List<CategoryResponseDto> getItemsBycommonIdx(Long commonIdx) {
    	List<Item> items = itemRepository.findByCategory_commonIdx(commonIdx);
    	
    	
        return items.stream()
                    .map(CategoryResponseDto::new)
                    .collect(Collectors.toList());
    }
}

