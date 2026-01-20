package com.itwillbs.LaClave.memberaddress;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/member/address")
@RequiredArgsConstructor
public class MemberAddressController {

    private final MemberAddressService memberAddressService;
    
    // ✅ 1. 회원 주소 등록
    @PostMapping
    public ResponseEntity<Long> register(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody Memberaddress memberaddress) {
        Long addressIdx = memberAddressService.register(memberaddress, user.getMemberIdx());
        return ResponseEntity.ok(addressIdx);
    }

    // ✅ 2. 회원 주소 목록 조회
    @GetMapping
    public ResponseEntity<List<MemberAddressDto>> getMyAddressList(
            @AuthenticationPrincipal CustomUserDetails user) {
        List<MemberAddressDto> addresses = memberAddressService.getMyAddressList(user);
        return ResponseEntity.ok(addresses);
    }

    // ✅ 3. 특정 주소 조회
    @GetMapping("/{addressIdx}")
    public ResponseEntity<MemberAddressDto> get(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("addressIdx") Long addressIdx) {
        MemberAddressDto address = memberAddressService.get(addressIdx, user);
        return ResponseEntity.ok(address);
    }

    // ✅ 4. 회원 주소 수정
    @PutMapping("/{addressIdx}")
    public ResponseEntity<String> modify(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("addressIdx") Long addressIdx,
            @RequestBody Memberaddress updatedAddress) {
        updatedAddress.setAddressIdx(addressIdx); // PathVariable로 넘어온 주소 idx 설정
        memberAddressService.modify(updatedAddress, user);
        return ResponseEntity.ok("주소가 수정되었습니다.");
    }

    // ✅ 5. 회원 주소 삭제
    @DeleteMapping("/{addressIdx}")
    public ResponseEntity<String> remove(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("addressIdx") Long addressIdx) {
        memberAddressService.remove(addressIdx, user);
        return ResponseEntity.ok("주소가 삭제되었습니다.");
    }


}
