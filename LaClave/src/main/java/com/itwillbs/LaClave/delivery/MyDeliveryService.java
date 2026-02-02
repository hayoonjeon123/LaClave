package com.itwillbs.LaClave.delivery;

import java.util.List;

public interface MyDeliveryService {
//	//주문별 배송지 조회
//	List<MyDelivery> getDeliveryByOrder(Long orderIdx);
	
	//마이페이지 배송지 목록 조회용
	List<MyDelivery> getMyDeliveryListByMember(Long memberIdx);
	
	List<MyDeliveryDto> getDeliveryByOrder(Long orderIdx);

}
