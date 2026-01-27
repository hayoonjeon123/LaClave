package com.itwillbs.LaClave.recent;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class RecentProductDto {
    private Long productIdx;
    private String productName;
    private int price;
    private LocalDateTime viewedAt;
}