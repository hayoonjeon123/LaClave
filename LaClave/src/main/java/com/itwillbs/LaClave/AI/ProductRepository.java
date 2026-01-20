//package com.itwillbs.LaClave.AI;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.itwillbs.LaClave.Category.Item;
//
//public interface ProductRepository extends JpaRepository<Item, Long> {
//    // PRODUCT_VECTOR 컬럼이 비어있는 상품들만 가져오는 메서드
//    List<Item> findAllByProductVectorIsNull();
//}
//
