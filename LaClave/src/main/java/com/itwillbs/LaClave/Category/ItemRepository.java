package com.itwillbs.LaClave.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory_commonIdx(Long commonIdx);
    

}

