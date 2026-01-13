package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PRODUCT")
@Data
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_IDX") 
    private Long productIdx;

    @Column(name = "STATUS_COMMON_IDX", nullable = false) 
    private Long statusCommonIdx;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 200) 
    private String productName;

    @Column(name = "PRODUCT_SHORT_DESC", length = 500) 
    private String productShortDesc;

    @Column(name = "PRODUCT_DETAIL_DESC", columnDefinition = "CLOB") 
    private String productDetailDesc;

    @Column(name = "PRODUCT_SIZE_GUIDE", columnDefinition = "CLOB") 
    private String productSizeGuide;

    @Column(name = "PRODUCT_MATERIAL", length = 200) 
    private String productMaterial;

    @Column(name = "TEXTURE_INFO", length = 200) 
    private String productTextureInfo;

    @Column(name = "PRODUCT_PRICE", nullable = false) 
    private int productPrice;

    @Column(name = "DISCOUNT_RATE", nullable = false) 
    private int productDiscountRate = 0;

    @Column(name = "STOCK_QTY", nullable = false) 
    private int productStockQty;

    @Column(name = "CREATED_AT", nullable = false, updatable = false) 
    private java.time.LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false) 
    private java.time.LocalDateTime updatedAt;

    // 카테고리와의 연결 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_IDX") 
    private Category category;
    
    //옵션테이블 연결
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ProductOption> options = new ArrayList<>();
}