package com.itwillbs.LaClave.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long cartItemIdx;
    private Long productIdx;
    private String productName;
    private String colorName;
    private String sizeName;
    private Integer price;
    private Integer quantity;
    private String imageUrl;
}
