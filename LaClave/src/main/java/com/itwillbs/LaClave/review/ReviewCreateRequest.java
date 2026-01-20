package com.itwillbs.LaClave.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {	
	private Integer ordersIdx;
    private Integer productIdx;
    private Double score;
    private String content;


}
