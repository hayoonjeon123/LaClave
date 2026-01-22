package com.itwillbs.LaClave.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByMemberId(String memberId);
	boolean existsByMemberId(String memberId);
	boolean existsByEmail(String email);

	// 아이디 찾기
	Optional<Member> findByMemberNameAndEmail(String memberName, String email);

	Optional<Member> findByMemberIdAndMemberNameAndEmail(String memberId, String memberName, String email);
	
	

}
