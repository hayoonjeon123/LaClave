package com.itwillbs.LaClave.point;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwillbs.LaClave.member.Member;

public interface PointRepository extends JpaRepository<Point, Long> {
	
	// 회원별 포인트 조회
	 List<Point> findByMemberOrderByCreatedAtDesc(Member member);
}