package com.itwillbs.LaClave.orders;

import java.util.List;

import com.itwillbs.LaClave.config.CustomUserDetails;
import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.payment.OrderCreateRequestDto;
import com.itwillbs.LaClave.payment.PaymentApprovalRequestDto;


public interface OrdersService {
	
	List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user);
	
//    List<MyOrderResponseDto> getMyOrderList(Integer memberIdx);
	
	//주문 생성 및 결제 승인 메서드
    String createOrder(Member member, OrderCreateRequestDto dto);
    boolean approvePayment(PaymentApprovalRequestDto dto);

    //결제 
	Orders findByOrderNo(String orderNo);

}
