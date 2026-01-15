package com.itwillbs.LaClave.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
	

    @Query("SELECT DISTINCT o FROM Orders o " +
            "LEFT JOIN FETCH o.orderDetails od " +
            "WHERE o.memberIdx = :memberIdx " +
            "ORDER BY o.ordersDate DESC")
     List<Orders> findAllWithDetailsByMemberIdx(@Param("memberIdx") Long memberIdx);
}


