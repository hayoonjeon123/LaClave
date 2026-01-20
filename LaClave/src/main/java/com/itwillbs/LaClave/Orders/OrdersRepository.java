package com.itwillbs.LaClave.Orders;

import java.util.List;
import java.util.Optional;

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
	
	// 주문번호(String)로 주문 정보를 찾는 메서드 추가
    Optional<Orders> findByOrderNo(String orderNo);
    
    
    @Query("""
    	    SELECT od
    	    FROM OrdersDetail od
    	    LEFT JOIN Review r ON od.product.productIdx = r.product.productIdx
    	        AND r.member.memberIdx = :memberIdx
    	    WHERE od.order.member.memberIdx = :memberIdx
    	      AND r.reviewIdx IS NULL
    	""")
    	List<OrdersDetail> findUnreviewedDetailsByMember(@Param("memberIdx") Long memberIdx);
}



