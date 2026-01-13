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


    @Column(name = "COLOR_COMMON_IDX")
    private Long colorCommonIdx;
    
    @Column(name = "SIZE_COMMON_IDX")
    private Long sizeCommonIdx;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_IDX")
    private Item item;
    

}
