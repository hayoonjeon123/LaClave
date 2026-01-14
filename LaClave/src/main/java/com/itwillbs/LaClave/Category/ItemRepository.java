package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	// 하위 카테고리 조회 (연관된 옵션까지 한 번에!)
    @EntityGraph(attributePaths = {"options", "options.colorCategory", "options.sizeCategory"})
    List<Item> findByProductCategoryIdx(Long productCategoryIdx);

    // 상위 카테고리 조회 (연관된 옵션까지 한 번에!)
    @EntityGraph(attributePaths = {"options", "options.colorCategory", "options.sizeCategory"})
    List<Item> findByProductCommonIdx(Long productCommonIdx);
    

}

