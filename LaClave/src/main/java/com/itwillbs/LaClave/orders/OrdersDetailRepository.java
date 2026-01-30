package com.itwillbs.LaClave.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
	
	 List<OrdersDetail> findByOrder_OrdersIdx(Long ordersIdx);
	 @Query("SELECT od FROM OrdersDetail od WHERE od.order.ordersIdx = :ordersIdx AND od.productIdx = :productIdx")
	    Optional<OrdersDetail> findByOrdersIdxAndProductIdx(
	        @Param("ordersIdx") Long ordersIdx, 
	        @Param("productIdx") Long productIdx
	    );
	 
	   Optional<OrdersDetail> findFirstByOrder_OrdersIdxOrderByOrdersDetailIdxAsc(Long ordersIdx);

}
