package com.itwillbs.LaClave.recent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentProductRepository extends JpaRepository<RecentProduct, Integer>{
	
	//회원의 최근 본 상품 목록
	List<RecentProduct> findByMemberIdxOrderByViewedAtDesc(Integer memberIdx);
	
	//마이페이지 최근본 상품 5개 
	List<RecentProduct> findTop5ByMemberIdxOrderByViewedAtDesc(Integer memberIdx);

}
