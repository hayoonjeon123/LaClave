package com.itwillbs.LaClave.recent;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class RecentProductDto {
    private Long productIdx;
    private String productName;
    private int price;
    private LocalDateTime viewedAt;


    private String productImageUrl;
    

    // ✅ JPQL 전용 생성자
    public RecentProductDto(
        Long productIdx,
        String productName,
        int price,
        LocalDateTime viewedAt
    ) {
        this.productIdx = productIdx;
        this.productName = productName;
        this.price = price;
        this.viewedAt = viewedAt;
    }
}