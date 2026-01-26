package com.itwillbs.LaClave.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByMember_MemberIdxAndStatus(Long memberIdx, String status);

    List<Review> findByProduct_ProductIdxAndStatus(Long productIdx, String status);

    @Query("""
        SELECT AVG(r.score)
        FROM Review r
        WHERE r.product.productIdx = :productIdx
          AND r.status = 'ACTIVE'
    """)
    Double getAverageScoreByProduct(@Param("productIdx") Long productIdx);

    @Query("""
        SELECT COUNT(r)
        FROM Review r
        WHERE r.product.productIdx = :productIdx
          AND r.status = 'ACTIVE'
    """)
    Long countActiveReviewsByProduct(@Param("productIdx") Long productIdx);

    boolean existsByMember_MemberIdxAndOrdersIdxAndProduct_ProductIdx(
        Long memberIdx,
        Integer ordersIdx,
        Long productIdx
    );
    
    @Query("""
    	    SELECT COUNT(r)
    	    FROM Review r
    	    WHERE r.product.productIdx = :productIdx
    	      AND r.status = 'ACTIVE'
    	""")
    	Integer countByProductIdx(@Param("productIdx") Long productIdx);
    
    @Query("""
    	    SELECT r
    	    FROM Review r
    	    WHERE r.product.productIdx = :productIdx
    	""")
    	List<Review> findByProductIdx(@Param("productIdx") Integer productIdx);
}

