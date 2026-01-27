package com.itwillbs.LaClave.payment;

import com.itwillbs.LaClave.orders.Orders;
import com.itwillbs.LaClave.orders.OrdersDetail;

import lombok.Data;

// 주문 상품의 상세 정보 
@Data
public class OrderDetailRequestDto {
    private Long productIdx; 
    private String productName;
    private String colorCode; 
    private String sizeCode; 
    private Integer quantity; 
    private Integer price; 
    private Integer discountPrice; 

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
