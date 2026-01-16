package com.itwillbs.LaClave.PayMent;

import java.time.LocalDateTime;
import java.util.List;

import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.memberaddress.Memberaddress;

import groovy.transform.builder.Builder;
import lombok.Data;

@Data
public class OrderCreateRequestDto {
    private Long addrIdx;

    private Integer usedPoint;

    private Integer totalPrice;

    private String deliveryMsg;

    private List<OrderDetailRequestDto> orderItems;

    public Orders toOrderEntity(Member member, Memberaddress addr, String orderNo) {
    	
        return Orders.builder()
                .member(member)
                .orderNo(orderNo)
                .ordersDate(LocalDateTime.now())
                .ordersStatus(160L)
                .totalPrice(this.totalPrice)
                .usedPoint(this.usedPoint)
                .recipientName(addr.getRecipientName())
                .phone(addr.getPhone())
                .postCode(addr.getPostCode())
                .address(addr.getAddress())
                .addressDetail(addr.getAddressDetail())
                .deliveryMsg(this.deliveryMsg)
                .build();
    }
}
