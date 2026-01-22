package com.itwillbs.LaClave.wishlist;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                .wishlistDate(wishlist.getWishlistDate())
                .build();
    }
}
