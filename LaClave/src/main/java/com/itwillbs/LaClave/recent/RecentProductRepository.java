package com.itwillbs.LaClave.recent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentProductRepository extends JpaRepository<RecentProduct, Integer>{
	
	//회원의 최근 본 상품 목록
	List<RecentProduct> findByMemberIdxOrderByViewedAtDesc(Integer memberIdx);

}
