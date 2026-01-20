package com.itwillbs.LaClave.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itwillbs.LaClave.Member.Member;



public interface CouponRepository extends JpaRepository<Coupon, Long>{
	
	List<Coupon> findByMember(Member member);

}
