package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/")
@Log4j2
public class CategoryController {
    private final ItemService itemService;

    public CategoryController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/category/{commonIdx}")
    public List<CategoryResponseDto> getCategoryItems(@PathVariable("commonIdx") Long commonIdx) {
        return itemService.getItemsBycommonIdx(commonIdx);
    }
}
