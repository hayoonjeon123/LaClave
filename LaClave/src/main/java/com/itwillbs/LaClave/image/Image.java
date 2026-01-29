package com.itwillbs.LaClave.image;

import com.itwillbs.LaClave.category.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "IMAGE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "item")
@EqualsAndHashCode(exclude = "item")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_gen")
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "IMAGE_SEQ", allocationSize = 1)
    @Column(name = "IMAGE_IDX")
    private Long imageIdx;

    @Column(name = "TARGET_CODE")
    private String targetCode;

    @Column(name = "TARGET_TYPE")
    private String targetType;

    @Column(name = "TARGET_IDX")
    private Integer targetIdx;

    @Column(name = "IMAGE_URL", length = 1000)
    private String imageUrl;

    // Item 상품 이미지 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_IDX", referencedColumnName = "PRODUCT_IDX", insertable = false, updatable = false)
    private Item item;

    public static Image createProductImage(String imageUrl, String targetCode, String targetType, Item item) {
        return Image.builder()
                .imageUrl(imageUrl)
                .targetCode(targetCode)
                .targetType(targetType)
                .targetIdx(item != null ? item.getProductIdx().intValue() : null)
                .build();
    }

    public String getUrl() {
        return this.imageUrl;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }
}
