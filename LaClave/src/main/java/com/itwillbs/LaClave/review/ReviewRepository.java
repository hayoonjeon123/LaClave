package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review,Long>{
	
	// 내가쓴 리뷰 목록
	List<Review> findAllByMemberIdx(Long memberIdx);
	
	// 상품별 리뷰 목록 조회 
    List<Review> findByProductIdxAndStatus(Long productIdx, String status);
    
    // 상품별 평균 점수 조회
    @Query("SELECT ROUND(AVG(r.score), 1) FROM Review r WHERE r.productIdx = :productIdx AND r.status = 'ACTIVE'")
    Double getAverageScoreByProduct(@Param("productIdx") Long productIdx);
    //
	List<Review> findByProductIdx(int intValue);
}
