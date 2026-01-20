package com.itwillbs.LaClave.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = { "images", "options", "options.colorCategory", "options.sizeCategory" })
    List<Item> findByProductCategoryIdx(Long productCategoryIdx);

    @EntityGraph(attributePaths = { "images", "options", "options.colorCategory", "options.sizeCategory" })
    List<Item> findByProductCommonIdx(Long productCommonIdx);

    @EntityGraph(attributePaths = { "images", "options", "options.colorCategory", "options.sizeCategory" })
    Optional<Item> findById(Long productIdx);

    @EntityGraph(attributePaths = { "images", "options", "options.colorCategory", "options.sizeCategory" })

    List<Item> findAll();

    // ai 상품명 또는 스타일태그로 검색 (OR 조건)
    List<Item> findByProductNameContainingOrStyleTagsContaining(String productName, String styleTags);

    // 상품명 검색
    List<Item> findByProductNameContaining(String keyword);

    // 통합 검색 (상품명, 스타일태그, 카테고리명 포함)
    @Query("SELECT i FROM Item i " +
            "WHERE i.productName LIKE %:keyword% " +
            "OR i.styleTags LIKE %:keyword% " +
            "OR i.productCategoryIdx IN (SELECT c.commonIdx FROM Category c WHERE c.codeDesc LIKE %:keyword%) " +
            "OR i.productCommonIdx IN (SELECT c.commonIdx FROM Category c WHERE c.codeDesc LIKE %:keyword%)")
    List<Item> searchByKeyword(@Param("keyword") String keyword);
}
