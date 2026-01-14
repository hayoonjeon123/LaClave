package com.itwillbs.LaClave;

import com.itwillbs.LaClave.Category.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "item")
@EqualsAndHashCode(exclude = "item")
@Table(name = "IMAGE")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_IDX") // DB의 IMAGE_IDX와 매핑
    private Long id;

    @Column(name = "TARGET_CODE")
    private String targetCode; // 예: img_type

    @Column(name = "TARGET_TYPE")
    private String targetType; // 예: img_01, img_02 등

    @Column(name = "IMAGE_URL") // DB의 IMAGE_URL과 매핑
    private String url;

    // Item과의 연결 (TARGET_IDX가 PRODUCT_IDX를 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_IDX", referencedColumnName = "PRODUCT_IDX")
    private Item item;
}
