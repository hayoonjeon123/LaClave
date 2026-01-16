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
    private OptionInfo color;
    private OptionInfo size;
    private Integer price;
    private Integer quantity;
    private String imageUrl;
    
    @Data
    @AllArgsConstructor
    public static class OptionInfo {
        private Long commonIdx;  // 저장용 PK
        private String codeName; // 표시용 이름
    }
}
