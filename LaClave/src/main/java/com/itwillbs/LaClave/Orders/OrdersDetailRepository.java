package com.itwillbs.LaClave.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
	
	 List<OrdersDetail> findByOrder_OrdersIdx(Long ordersIdx);

}
