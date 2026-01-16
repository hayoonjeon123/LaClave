package com.itwillbs.LaClave.Orders;

import java.util.List;

import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.PayMent.OrderCreateRequestDto;
import com.itwillbs.LaClave.PayMent.PaymentApprovalRequestDto;
import com.itwillbs.LaClave.security.CustomUserDetails;

public interface OrdersService {
	
	List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user);
	
//    List<MyOrderResponseDto> getMyOrderList(Integer memberIdx);
	
	//주문 생성 및 결제 승인 메서드
    String createOrder(Member member, OrderCreateRequestDto dto);
    boolean approvePayment(PaymentApprovalRequestDto dto);

    //결제 
	Orders findByOrderNo(String orderNo);

}
