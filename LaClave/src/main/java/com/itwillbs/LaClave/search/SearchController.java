package com.itwillbs.LaClave.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.category.CategoryResponseDto;
import com.itwillbs.LaClave.category.Item;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Log4j2
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> search(ItemSearchCondition condition) {
        List<Item> items = searchService.searchItems(condition);

        List<CategoryResponseDto> dtos = items.stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
