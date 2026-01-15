package com.itwillbs.LaClave.memberaddress;

public interface MemberAddressService {
	
	// 회원 배송지 작성하기
	Long register(Memberaddress memberaddress);
	//회원 배송지 조회
	Memberaddress get(Long addressIdx);
	// 회원 배송지 수정하기
	void modify(Memberaddress memberaddress);
	// 회원 배송지 삭제하기
	void remove(Long addressIdx);

}
