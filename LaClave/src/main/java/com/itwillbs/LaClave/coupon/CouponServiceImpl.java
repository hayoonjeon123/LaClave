package com.itwillbs.LaClave.coupon;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.Config.CustomUserDetails;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor //생성자 자동 생성
public class CouponServiceImpl implements CouponService{
	
	private final CouponRepository couponRepository;
	
    // 회원별 문의 목록 조회 (로그인 사용자 기준)
	@Override
	public List<CouponDto> getMyCouponList(CustomUserDetails user) {
	    List<Coupon> coupons = couponRepository.findByMember(user.getMember());
	    return coupons.stream()
	                  .map(c -> new CouponDto(
	                      c.getCouponIdx(),
	                      c.getCouponName(),
	                      c.getDiscountValue(),
	                      c.getMinOrderPrice(),
	                      c.getStartDate(),
	                      c.getEndDate(),
	                      c.getUsedStatus()
	                  ))
	                  .collect(Collectors.toList());
	}

}
