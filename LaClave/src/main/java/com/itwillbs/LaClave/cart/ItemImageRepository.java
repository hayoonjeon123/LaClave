package com.itwillbs.LaClave.cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.itwillbs.LaClave.cart.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
	// 이미지 조회
    @Query(value = "SELECT * FROM IMAGE WHERE TARGET_IDX = :targetIdx AND TARGET_CODE = :targetCode", nativeQuery = true)
    List<ItemImage> findByTargetIdxAndCode(@Param("targetIdx") Integer targetIdx,
            @Param("targetCode") String targetCode);
}
