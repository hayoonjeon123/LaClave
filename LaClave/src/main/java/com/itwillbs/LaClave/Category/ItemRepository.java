package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	// 해석: Item(상품) 중에서 Category(카테고리)의 Code(ID)가 일치하는 것들을 다 찾아라!
    List<Item> findByCategory_commonIdx(Long commonIdx);
    

}

