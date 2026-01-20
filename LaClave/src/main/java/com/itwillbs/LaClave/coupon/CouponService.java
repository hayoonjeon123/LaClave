package com.itwillbs.LaClave.coupon;

import java.util.List;

import com.itwillbs.LaClave.security.CustomUserDetails;

public interface CouponService {
	
	List<CouponDto> getMyCouponList(CustomUserDetails user);

}
