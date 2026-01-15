package com.itwillbs.LaClave.memberaddress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberAddressRepository extends JpaRepository<Memberaddress, Long> {
	
	/* 회원별 배송지 목록*/ //SELECT * FROM member_address WHERE member_idx = ?
	List <Memberaddress> findByMemberIdx(Integer memberIdx);
	

	
    @Query("SELECT m FROM Memberaddress m WHERE m.memberIdx = :memberIdx")
    Memberaddress findDefaultByMemberIdx(@Param("memberIdx") Long  memberIdx);

}
