package com.itwillbs.LaClave.review;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewUpdateRequest {

    private Double score;   
    private String content; 
}