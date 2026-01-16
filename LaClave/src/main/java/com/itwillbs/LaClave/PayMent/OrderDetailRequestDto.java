package com.itwillbs.LaClave.PayMent;

import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersDetail;

import lombok.Data;

@Data
public class OrderDetailRequestDto {
    private Long productIdx; // 상품 PK
    private String productName; // 상품명 (스냅샷용)
    private String colorCode; // 선택한 색상
    private String sizeCode; // 선택한 사이즈
    private Integer quantity; // 수량
    private Integer price; // 상품 단가
    private Integer discountPrice; // 할인 금액

    public OrdersDetail toDetailEntity(Orders order) {
        return OrdersDetail.builder()
                .order(order)
                .productIdx(this.productIdx)
                .productName(this.productName)
                .colorCode(Long.parseLong(String.valueOf(this.colorCode))) 
                .sizeCode(Long.parseLong(String.valueOf(this.sizeCode)))
                .quantity(Long.parseLong(String.valueOf(this.quantity)))
                .price(Long.parseLong(String.valueOf(this.price)))
//                .discountPrice(this.discountPrice != null ? this.discountPrice : 0)
                // totalPrice는 DB에서 가상 열로 자동 계산됨
                .build();
    }
}
