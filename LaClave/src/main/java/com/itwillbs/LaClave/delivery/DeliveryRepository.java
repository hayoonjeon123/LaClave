package com.itwillbs.LaClave.delivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<MyDelivery, Integer>{
	
	// 주문별 배송 조회 
	List<MyDelivery> findByOrderIdx(Integer orderIdx);
	
	// 주문 여러개 조회하고싶을때
	List<MyDelivery> findByOrderIdxIn(List<Integer> orderIdxList);
	
}
