package com.itwillbs.LaClave.delivery;

import java.util.List;

public interface MyDeliveryService {
	
	//나의 배송지 데이터 들고오기용
	MyDelivery getMyDelivery(Integer deliveryIdx);
	//마이페이지 배송지 목록 조회용
//	List<MyDelivery> getMyDeliveryListByMember(Integer memberIdx);
	
	

}
