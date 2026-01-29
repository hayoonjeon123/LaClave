package com.itwillbs.LaClave.orders;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.itwillbs.LaClave.PayMent.PayMent;



@Data
public class MyOrderResponseDto {

    private final Long ordersIdx;
    private String orderNo;
    private final LocalDateTime ordersDate;
    private final Long ordersStatus; // 공통코드 PK
    private final Integer totalPrice;
    private final String deliveryMsg;
    private final DeliveryInfoDto delivery; // 배송지 정보
    private final List<OrderDetailDto> details; // 주문 상세 목록
    private final Optional<PayMentInfoDto> payInfo; // 결제 정보
    

    public MyOrderResponseDto(Orders orderEntity) {
        this.ordersIdx = orderEntity.getOrdersIdx();
        this.orderNo = orderEntity.getOrderNo();
        this.ordersDate = orderEntity.getOrdersDate();
        this.ordersStatus = orderEntity.getOrdersStatus();
        this.totalPrice = orderEntity.getTotalPrice();
        this.deliveryMsg = orderEntity.getDeliveryMsg();

        this.details = (orderEntity.getOrderDetails() != null)
                ? orderEntity.getOrderDetails().stream()
                    .map(OrderDetailDto::new)
                    .collect(Collectors.toList())
                : List.of();

        this.delivery = new DeliveryInfoDto(orderEntity);

        // 결제 정보 optional 처리
        this.payInfo = Optional.ofNullable(
            orderEntity.getPayment() != null
                ? new PayMentInfoDto(orderEntity.getPayment())
                : null
        );
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
    private String productImageUrl; // ⭐ 추가


    public OrderDetailDto(OrdersDetail detail) {
        this.productIdx = detail.getProductIdx();
        this.productName = detail.getProductName();
        this.quantity = detail.getQuantity();
        this.price = detail.getPrice();
        this.totalPrice = detail.getTotalPrice();
        this.colorCode = detail.getColorCode();
        this.sizeCode = detail.getSizeCode();
       
    }

    // Service에서 주입용
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
		
	}
}

@Getter
class DeliveryInfoDto {
    private final String recipientName;
    private final String phone;
    private final String address;
    private final String addressDetail;

    public DeliveryInfoDto(Orders order) {
        this.recipientName = order.getRecipientName();
        this.phone = order.getPhone();
        this.address = order.getAddress();
        this.addressDetail = order.getAddressDetail();
    }
}

@Getter
class PayMentInfoDto {
    private final Long paymentIdx;
    private final LocalDateTime paymentDate;
    private final Integer totalPrice;
    private final Integer payStatus;
    private final Integer payWay;
    private final Integer payType;
    private final Integer payReference;
    private final String externalTransaction;

    private String payWayName;
    private String payStatusName;
    private String payTypeName;

    public PayMentInfoDto(PayMent payment) {
        this.paymentIdx = payment.getPaymentIdx();
        this.paymentDate = payment.getPaymentDate();
        this.totalPrice = payment.getTotalPrice();
        this.payStatus = payment.getPayStatus();
        this.payWay = payment.getPayWay();
        this.payType = payment.getPayType();
        this.payReference = payment.getPayReference();
        this.externalTransaction = payment.getExternalTransaction();
    }

    public void setPayWayName(String payWayName) { this.payWayName = payWayName; }
    public void setPayStatusName(String payStatusName) { this.payStatusName = payStatusName; }
    public void setPayTypeName(String payTypeName) { this.payTypeName = payTypeName; }
}
