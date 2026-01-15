package com.itwillbs.LaClave.Orders;

import java.util.List;

import com.itwillbs.LaClave.security.CustomUserDetails;

public interface OrdersService {
	
	List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user);

}
