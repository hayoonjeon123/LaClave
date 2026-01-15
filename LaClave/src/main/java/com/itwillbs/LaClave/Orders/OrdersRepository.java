package com.itwillbs.LaClave.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{
	

//    @Query("SELECT DISTINCT o FROM Orders o " +
//            "LEFT JOIN FETCH o.orderDetails od " +
//            "WHERE o.memberIdx = :memberIdx " +
//            "ORDER BY o.ordersDate DESC")
//     List<Orders> findAllWithDetailsByMemberIdx(@Param("memberIdx") Long memberIdx);
	@Query(value = "SELECT * FROM ORDERS WHERE MEMBER_IDX = :memberIdx ORDER BY ORDERS_DATE DESC", nativeQuery = true)
	List<Orders> findAllByMemberIdxNative(@Param("memberIdx") Long  memberIdx);
}


