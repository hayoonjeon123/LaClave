package com.itwillbs.LaClave.Cart;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.Member.MemberRepository;
import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Category.ProductOption;
import com.itwillbs.LaClave.Category.Category;
import com.itwillbs.LaClave.Category.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 장바구니 담기
    @Transactional
    public void addCart(CartRequestDto dto, String memberId) {
        // 1. 회원 조회
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Long mIdx = member.getMemberIdx();

        // 2. 장바구니 조회/생성
        Cart cart = cartRepository.findByMemberIdx(mIdx)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMemberIdx(mIdx);
                    return cartRepository.save(newCart);
                });

        // 3. 상품 상세 조회 (옵션 매칭용)
        Item item = itemRepository.findById(dto.getProductIdx())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Long foundColorCode = null;
        Long foundSizeCode = null;

        // 4. 문자열(color, size)을 실제 DB 카테고리 ID로 변환
        if (item.getOptions() != null) {
            for (ProductOption option : item.getOptions()) {
                boolean colorMatch = false;
                boolean sizeMatch = false;

                // 색상 비교 (DTO의 getColor() 사용)
                if (option.getColorCategory() != null) {
                    Category colorCat = option.getColorCategory();
                    String hex = colorCat.getCodeDesc();
                    String code = colorCat.getCode();
                    if ((hex != null && hex.equalsIgnoreCase(dto.getColor()))
                            || (code != null && code.equalsIgnoreCase(dto.getColor()))) {
                        colorMatch = true;
                    }
                }

                // 사이즈 비교 (DTO의 getSize() 사용)
                if (option.getSizeCategory() != null) {
                    Category sizeCat = option.getSizeCategory();
                    String code = sizeCat.getCode();
                    if (code != null && code.equalsIgnoreCase(dto.getSize())) {
                        sizeMatch = true;
                    }
                }

                if (colorMatch && sizeMatch) {
                    foundColorCode = option.getColorCategory().getCommonIdx();
                    foundSizeCode = option.getSizeCategory().getCommonIdx();
                    break;
                }
            }
        }

        if (foundColorCode == null || foundSizeCode == null) {
            throw new RuntimeException("유효하지 않은 옵션입니다. [color: " + dto.getColor() + ", size: " + dto.getSize() + "]");
        }

        // 5. 중복 확인 및 저장
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartAndProductIdxAndColorCodeAndSizeCode(
                        cart, dto.getProductIdx(), foundColorCode, foundSizeCode);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .productIdx(dto.getProductIdx())
                    .colorCode(foundColorCode)
                    .sizeCode(foundSizeCode)
                    .quantity(dto.getQuantity())
                    .price(dto.getPrice())
                    .discountPrice(dto.getDiscountPrice())
                    .build();
            cartItemRepository.save(newItem);
        }
    }

    // 장바구니 목록 조회
    @Transactional
    public java.util.List<CartResponseDto> getCartItems(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMemberIdx(member.getMemberIdx())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMemberIdx(member.getMemberIdx());
                    return cartRepository.save(newCart);
                });

        return cart.getCartItems().stream().map(item -> {
            Item product = itemRepository.findById(item.getProductIdx()).orElse(null);
            Category color = categoryRepository.findById(item.getColorCode()).orElse(null);
            Category size = categoryRepository.findById(item.getSizeCode()).orElse(null);

            String imageUrl = "";
            if (product != null && product.getImages() != null && !product.getImages().isEmpty()) {
                imageUrl = product.getImages().iterator().next().getUrl();
            }

            return CartResponseDto.builder()
                    .cartItemIdx(item.getCartItemIdx())
                    .productIdx(item.getProductIdx())
                    .productName(product != null ? product.getProductName() : "Unknown")
                    .colorName(color != null ? color.getCode() : "Unknown")
                    .sizeName(size != null ? size.getCode() : "Unknown")
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .imageUrl(imageUrl)
                    .build();
        }).toList();
    }

    // 장바구니 삭제
    @Transactional
    public void deleteCartItem(Long cartItemIdx, String memberId) {
        CartItem cartItem = cartItemRepository.findById(cartItemIdx)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 자신이 소유한 장바구니 품목인지 확인
        if (!cartItem.getCart().getMemberIdx().equals(member.getMemberIdx())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        cartItemRepository.delete(cartItem);
    }
}
