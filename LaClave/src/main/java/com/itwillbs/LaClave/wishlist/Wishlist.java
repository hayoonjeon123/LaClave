package com.itwillbs.LaClave.wishlist;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Builder
@Table(name = "WISHLIST")
public class Wishlist {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WISHLIST_IDX")
    private Integer wishlistIdx;

    @Column(name = "MEMBER_IDX", nullable = false)
    private Integer memberIdx;

    @Column(name = "PRODUCT_IDX", nullable = false)
    private Integer productIdx;

    @Column(name = "WISHLIST_DATE", nullable = false)
    private LocalDateTime wishlistDate;

}
