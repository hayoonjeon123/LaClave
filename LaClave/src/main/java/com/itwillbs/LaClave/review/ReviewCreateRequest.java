package com.itwillbs.LaClave.review;

import com.itwillbs.LaClave.category.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
    private Integer ordersIdx;
    private Long productIdx; 
    private Double score;
    private String content;
}
