package com.itwillbs.LaClave.Cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CART_ITEM")
@Data
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ITEM_IDX")
    private Long cartItemIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_IDX")
    private Cart cart;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productIdx;

    @Column(name = "COLOR_CODE", nullable = false)
    private Long colorCode; // DB NUMBER 타입에 맞춰 Long 사용

    @Column(name = "SIZE_CODE", nullable = false)
    private Long sizeCode; // DB NUMBER 타입에 맞춰 Long 사용

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "DISCOUNT_PRICE")
    private int discountPrice = 0;

    // 가상 컬럼(VIRTUAL)은 DB에서 계산하므로 insert/update 시 제외
    @Column(name = "TOTAL_PRICE", insertable = false, updatable = false)
    private int totalPrice;

    @Column(name = "IS_SELECTED")
    private int isSelected = 1;

    @Builder
    public CartItem(Cart cart, Long productIdx, Long colorCode, Long sizeCode, int quantity, int price,
            int discountPrice) {
        this.cart = cart;
        this.productIdx = productIdx;
        this.colorCode = colorCode;
        this.sizeCode = sizeCode;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.isSelected = 1;
    }
}
