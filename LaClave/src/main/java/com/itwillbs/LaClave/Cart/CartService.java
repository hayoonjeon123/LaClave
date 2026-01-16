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
        System.out.println("=== 장바구니 추가 시작 ===");
        System.out.println("요청된 color: " + dto.getColor());
        System.out.println("요청된 size: " + dto.getSize());

        if (item.getOptions() != null) {
            System.out.println("상품 옵션 개수: " + item.getOptions().size());

            for (ProductOption option : item.getOptions()) {
                boolean colorMatch = false;
                boolean sizeMatch = false;

                // 색상 비교 (DTO의 getColor() 사용)
                if (option.getColorCategory() != null) {
                    Category colorCat = option.getColorCategory();
                    String hex = colorCat.getCodeDesc();
                    String code = colorCat.getCode();
                    System.out.println("DB 색상 - code: " + code + ", hex: " + hex);

                    if ((hex != null && hex.equalsIgnoreCase(dto.getColor()))
                            || (code != null && code.equalsIgnoreCase(dto.getColor()))) {
                        colorMatch = true;
                        System.out.println("색상 매칭 성공!");
                    }
                }

                // 사이즈 비교 (DTO의 getSize() 사용)
                if (option.getSizeCategory() != null) {
                    Category sizeCat = option.getSizeCategory();
                    String code = sizeCat.getCode();
                    System.out.println("DB 사이즈 - code: " + code);

                    if (code != null && code.equalsIgnoreCase(dto.getSize())) {
                        sizeMatch = true;
                        System.out.println("사이즈 매칭 성공!");
                    }
                }

                if (colorMatch && sizeMatch) {
                    foundColorCode = option.getColorCategory().getCommonIdx();
                    foundSizeCode = option.getSizeCategory().getCommonIdx();
                    System.out.println("옵션 매칭 완료 - colorCode: " + foundColorCode + ", sizeCode: " + foundSizeCode);
                    break;
                }
            }
        }

        if (foundColorCode == null || foundSizeCode == null) {
            System.err.println("옵션 매칭 실패!");
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
        System.out.println("=== 장바구니 조회 시작 ===");
        System.out.println("memberId: " + memberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        System.out.println("회원 조회 성공 - memberIdx: " + member.getMemberIdx());

        Cart cart = cartRepository.findByMemberIdx(member.getMemberIdx())
                .orElseGet(() -> {
                    System.out.println("장바구니가 없어서 새로 생성");
                    Cart newCart = new Cart();
                    newCart.setMemberIdx(member.getMemberIdx());
                    return cartRepository.save(newCart);
                });

        System.out.println("장바구니 조회 성공 - cartIdx: " + cart.getCartIdx());
        System.out.println("장바구니 아이템 개수: " + (cart.getCartItems() != null ? cart.getCartItems().size() : 0));

        return cart.getCartItems().stream().map(item -> {
            System.out.println(
                    "처리 중인 아이템 - cartItemIdx: " + item.getCartItemIdx() + ", productIdx: " + item.getProductIdx());

            Item product = itemRepository.findById(item.getProductIdx()).orElse(null);
            Category color = categoryRepository.findById(item.getColorCode()).orElse(null);
            Category size = categoryRepository.findById(item.getSizeCode()).orElse(null);

            String imageUrl = "";
            if (product != null && product.getImages() != null && !product.getImages().isEmpty()) {
                imageUrl = product.getImages().iterator().next().getUrl();
            }

            CartResponseDto dto = CartResponseDto.builder()
                    .cartItemIdx(item.getCartItemIdx())
                    .productIdx(item.getProductIdx())
                    .productName(product != null ? product.getProductName() : "Unknown")
                    .color(new CartResponseDto.OptionInfo(
                    	    color != null ? color.getCommonIdx() : null, 
                    	    	    color != null ? color.getCode() : "Unknown"
                    	    	)) // ✅ 필드명과 동일하게 .color() 호출
                    	    	.size(new CartResponseDto.OptionInfo(
                    	    	    size != null ? size.getCommonIdx() : null, 
                    	    	    size != null ? size.getCode() : "Unknown"
                    	    	))
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .imageUrl(imageUrl)
                    .build();

            System.out.println("DTO 생성 완료: " + dto.getProductName());
            return dto;
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
