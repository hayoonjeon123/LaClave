package com.itwillbs.LaClave.recent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecentProductRepository extends JpaRepository<RecentProduct, Long>{
	
    // ✅ Entity 조회 (저장/업데이트용)
    Optional<RecentProduct> findByMemberIdxAndProductIdx(
            Integer memberIdx, Long productIdx);

    List<RecentProduct> findByMemberIdxOrderByViewedAtDesc(Long memberIdx);
    
    @Query("""
            SELECT new com.itwillbs.LaClave.recent.RecentProductDto(
                CAST(r.productIdx AS long),
                i.productName,
                i.productPrice,
                r.viewedAt
            )
            FROM RecentProduct r
            JOIN Item i ON i.productIdx = CAST(r.productIdx AS long)
            WHERE r.memberIdx = :memberIdx
            ORDER BY r.viewedAt DESC
        """)
        List<RecentProductDto> findRecentProductsWithPrice(
                @Param("memberIdx") Long memberIdx
        );
    



	Optional<RecentProduct> findByMemberIdxAndProductIdx(Long memberIdx, Long productIdx);
	
	
}
