package com.itwillbs.LaClave.Cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import com.itwillbs.LaClave.Cart.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    // TARGET_IDX(리뷰IDX)와 TARGET_CODE('REVIEW')로 이미지 조회
    // reviewIdx가 Integer이므로 String이나 Long 변환 주의. DB 컬럼 타입에 따라 다름.
    // 보통 TARGET_IDX는 숫자형일 것임.
    @Query(value = "SELECT * FROM IMAGE WHERE TARGET_IDX = :targetIdx AND TARGET_CODE = :targetCode", nativeQuery = true)
    List<ItemImage> findByTargetIdxAndCode(@Param("targetIdx") Integer targetIdx,
            @Param("targetCode") String targetCode);
}
