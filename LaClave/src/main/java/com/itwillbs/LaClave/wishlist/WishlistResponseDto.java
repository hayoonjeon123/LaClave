package com.itwillbs.LaClave.wishlist;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class WishlistResponseDto {

    private Integer wishlistIdx;
    private Integer productIdx;
    private String productName;
    private String imageUrl;
    private LocalDateTime wishlistDate;
    
    public static WishlistResponseDto from(Wishlist wishlist) {
        return WishlistResponseDto.builder()
                .wishlistIdx(wishlist.getWishlistIdx())
                .productIdx(wishlist.getProductIdx())
                .build();
    }

}
