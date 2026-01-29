package com.itwillbs.LaClave.category;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itwillbs.LaClave.image.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@ToString(exclude = { "options", "images" })
@EqualsAndHashCode(exclude = { "options", "images" })
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_IDX")
    private Long productIdx;

    @Column(name = "PRODUCT_COMMON_IDX")
    private Long productCommonIdx;

    @Column(name = "PRODUCT_SUBCATEGORY_IDX", insertable = false, updatable = false)
    private Long productSubcategoryIdx;

    @Column(name = "STATUS_COMMON_IDX", nullable = false)
    private Long statusCommonIdx;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 200)
    private String productName;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    private int productPrice;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private java.time.LocalDateTime updatedAt;

    @Column(name = "STYLE_TAGS", nullable = false)
    private String styleTags;

    // 카테고리와의 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @JoinColumn(name = "PRODUCT_SUBCATEGORY_IDX")
    private Category category;

    // 옵션테이블 연결
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private Set<ProductOption> options = new LinkedHashSet<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Image> images = new LinkedHashSet<>();

}