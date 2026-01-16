package com.itwillbs.LaClave.Orders;

import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.itwillbs.LaClave.memberaddress.Memberaddress;

@Data
public class MyOrderResponseDto {
    private final Long ordersIdx;
    private final LocalDateTime ordersDate;
    private final Long ordersStatus; // 공통코드 PK
    private final Integer totalPrice;

    private final DeliveryInfoDto delivery; // 배송지 정보
    private final List<OrderDetailDto> details; // 주문 상세 목록

    public MyOrderResponseDto(Orders orderEntity, List<OrdersDetail> detailsList, Memberaddress delivery) {
        this.ordersIdx = orderEntity.getOrdersIdx();
        this.ordersDate = orderEntity.getOrdersDate();
        this.ordersStatus = orderEntity.getOrdersStatus();
        this.totalPrice = orderEntity.getTotalPrice();

        this.details = (detailsList != null)
                ? detailsList.stream().map(OrderDetailDto::new).collect(Collectors.toList())
                : List.of();

        this.delivery = (delivery != null) ? new DeliveryInfoDto(delivery) : null;
    }
}

class OrderDetailDto {
    private final Long productIdx;
    private final Long quantity;
    private final Long price;
//    private final Long discountPrice;
    private final Long totalPrice;
    private final Long colorCode;
    private final Long sizeCode;

    public OrderDetailDto(OrdersDetail detail) {
        this.productIdx = detail.getProductIdx();
        this.quantity = detail.getQuantity();
        this.price = detail.getPrice();
//        this.discountPrice = detail.getDiscountPrice();
        this.totalPrice = detail.getTotalPrice();
        this.colorCode = detail.getColorCode();
        this.sizeCode = detail.getSizeCode();
    }
}

class DeliveryInfoDto {
    private final String recipientName;
    private final String phone;
    private final String address;
    private final String addressDetail;

    public DeliveryInfoDto(Memberaddress address) {
        this.recipientName = address.getRecipientName();
        this.phone = address.getPhone();
        this.address = address.getAddress();
        this.addressDetail = address.getAddressDetail();
    }
}
