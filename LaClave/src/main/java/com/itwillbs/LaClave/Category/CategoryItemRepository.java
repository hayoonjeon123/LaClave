package com.itwillbs.LaClave.Category;


import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryItemRepository extends JpaRepository<Item, Long> {
	
	// parentCode에 따라 하위메뉴 리스트로 반환
    List<Category> findByParentCode(String parentCode);

}

