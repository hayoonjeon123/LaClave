package com.itwillbs.LaClave.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchCondition {
    private String keyword;
    
    private Long categoryId;
    
    private Integer minPrice;
    
    private Integer maxPrice;
    
    private String sortType; 
}
