package com.itwillbs.LaClave.Member;

import java.util.List;

import lombok.Data;

@Data
public class AiInfoRequest {
	
	private Long memberIdx;
    private Double height;
    private Double weight;
    private List<String> styles; 

}
