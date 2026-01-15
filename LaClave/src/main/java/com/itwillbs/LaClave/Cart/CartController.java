package com.itwillbs.LaClave.Cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({ "/cart", "/api/cart" })
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	// 장바구니 담기
	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestBody CartRequestDto requestDto,
			@AuthenticationPrincipal UserDetails userDetails) {

		if (userDetails == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		String memberIdx = userDetails.getUsername();

		cartService.addCart(requestDto, userDetails.getUsername());

		return ResponseEntity.ok("장바구니에 담겼습니다.");
	}

	// 장바구니 조회
	@GetMapping("/list")
	public ResponseEntity<java.util.List<CartResponseDto>> getCartList(
			@AuthenticationPrincipal UserDetails userDetails) {

		if (userDetails == null) {
			return ResponseEntity.status(401).build();
		}

		return ResponseEntity.ok(cartService.getCartItems(userDetails.getUsername()));
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteCartItem(@RequestBody java.util.Map<String, Long> payload,
			@AuthenticationPrincipal UserDetails userDetails) {

		if (userDetails == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		Long cartItemIdx = payload.get("cartItemIdx");
		cartService.deleteCartItem(cartItemIdx, userDetails.getUsername());

		return ResponseEntity.ok("삭제되었습니다.");
	}
}