package com.itwillbs.LaClave.memberaddress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAddressRepository extends JpaRepository<Memberaddress, Integer> {
	
	/* 회원별 배송지 목록*/ //SELECT * FROM member_address WHERE member_idx = ?
	List <Memberaddress> findByMemberIdx(Integer memberIdx);
	

	

}
