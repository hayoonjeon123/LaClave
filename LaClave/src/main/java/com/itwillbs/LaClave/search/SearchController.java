package com.itwillbs.LaClave.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.category.Item;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchItemDto>> search(ItemSearchCondition condition) {
        List<Item> items = searchService.searchItems(condition);

        List<SearchItemDto> dtos = items.stream().map(item -> {
            String mainImageUrl = null;
            if (item.getImages() != null && !item.getImages().isEmpty()) {
                mainImageUrl = item.getImages().iterator().next().getUrl();
            }

            return new SearchItemDto(
                    item.getProductIdx(),
                    item.getProductName(),
                    item.getProductPrice(),
                    mainImageUrl);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
