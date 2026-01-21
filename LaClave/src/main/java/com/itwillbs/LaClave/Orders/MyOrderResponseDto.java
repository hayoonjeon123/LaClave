package com.itwillbs.LaClave.Orders;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.itwillbs.LaClave.commoncode.CommonCodeService;

@Data
public class MyOrderResponseDto {
	

	
    private final Long ordersIdx;
    private final LocalDateTime ordersDate;
    private final Long ordersStatus; // 공통코드 PK
    private final Integer totalPrice;

    private final DeliveryInfoDto delivery; // 배송지 정보
    private final List<OrderDetailDto> details; // 주문 상세 목록

    // 변경: Memberaddress 대신 Orders에서 직접 주소 가져오기
    public MyOrderResponseDto(Orders orderEntity) {
    	
    	
        this.ordersIdx = orderEntity.getOrdersIdx();
        this.ordersDate = orderEntity.getOrdersDate();
        this.ordersStatus = orderEntity.getOrdersStatus();
        this.totalPrice = orderEntity.getTotalPrice();

        this.details = (orderEntity.getOrderDetails() != null)
                ? orderEntity.getOrderDetails().stream().map(OrderDetailDto::new).collect(Collectors.toList())
                : List.of();

        // Orders에서 배송 정보 직접 가져오기
        this.delivery = new DeliveryInfoDto(orderEntity);
    }
}
@Getter
class OrderDetailDto {
	
	
    private final Long productIdx;
    private final String productName;
    private final Long quantity;
    private final Long price;
    private final Long totalPrice;
    private final Long colorCode;
    private final Long sizeCode;
    private String colorName;
    private String sizeName;



    public OrderDetailDto(OrdersDetail detail) {
    	
    	
        this.productIdx = detail.getProductIdx();
        this.productName = detail.getProductName(); 
        this.quantity = detail.getQuantity();
        this.price = detail.getPrice();
        this.totalPrice = detail.getTotalPrice();
        this.colorCode = detail.getColorCode();
        this.sizeCode = detail.getSizeCode();
        
    }



    // ⭐ Service에서 주입용
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
@Getter
class DeliveryInfoDto {
    private final String recipientName;
    private final String phone;
    private final String address;
    private final String addressDetail;

    // Orders에서 직접 배송 정보 가져오기
    public DeliveryInfoDto(Orders order) {
        this.recipientName = order.getRecipientName();
        this.phone = order.getPhone();
        this.address = order.getAddress();
        this.addressDetail = order.getAddressDetail();
    }
}
