package com.itwillbs.LaClave.Cart;

import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.review.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "item")
@EqualsAndHashCode(exclude = "item")
@Table(name = "IMAGE")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_IDX") 
    private Long id;

    @Column(name = "TARGET_CODE")
    private String targetCode; 

    @Column(name = "TARGET_TYPE")
    private String targetType; 

    @Column(name = "IMAGE_URL") 
    private String url;

    // Item과의 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_IDX", referencedColumnName = "PRODUCT_IDX")
    private Item item;

    // @ManyToOne
    // @JoinColumn(name = "REVIEW_IDX") // DB에 추가한 컬럼명과 일치시켜주세요
    // private Review review;
}
