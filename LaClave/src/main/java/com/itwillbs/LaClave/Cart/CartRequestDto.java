package com.itwillbs.LaClave.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    private Long productIdx;

    private String color;

    private String size;

    private int quantity;

    private int price;

    private int discountPrice;
}