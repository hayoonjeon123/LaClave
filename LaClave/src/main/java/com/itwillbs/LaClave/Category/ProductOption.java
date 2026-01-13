package com.itwillbs.LaClave.Category;

import com.itwillbs.LaClave.Category.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PRODUCT_OPTION")
@Data
public class ProductOption {
    @Id
    @Column(name = "OPTION_IDX")
    private Long optionIdx;

 // 색상 카테고리 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLOR_COMMON_IDX", insertable = false, updatable = false)
    private Category ColorCategory; 
//    private Category ColorCategory; 

    // 사이즈 카테고리 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIZE_COMMON_IDX", insertable = false, updatable = false)
    private Category sizeCategory;
//    private Category sizeCommonIdx;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_IDX")
    private Item item;
    

}
