package com.itwillbs.LaClave.coupon;

import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private Long couponIdx;
    private String couponName;
    private Integer discountValue;
    private Integer minOrderPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private String usedStatus;
}