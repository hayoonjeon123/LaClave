package com.itwillbs.LaClave.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
