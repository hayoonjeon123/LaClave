package com.itwillbs.LaClave.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IMAGE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_IDX")
    private Long imageIdx;

    @Column(name = "TARGET_CODE")
    private String targetCode;

    @Column(name = "TARGET_TYPE")
    private String targetType;

    @Column(name = "TARGET_IDX")
    private Long targetIdx;

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
