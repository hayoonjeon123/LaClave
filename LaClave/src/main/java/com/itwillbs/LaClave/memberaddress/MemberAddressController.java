package com.itwillbs.LaClave.memberaddress;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/member/address")
@RequiredArgsConstructor
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody Memberaddress memberaddress) {
        return ResponseEntity.ok(memberAddressService.register(memberaddress));
    }

    @GetMapping("/{addressIdx}")
    public ResponseEntity<Memberaddress> get(@PathVariable("addressIdx") Long addressIdx) {
        return ResponseEntity.ok(memberAddressService.get(addressIdx));
    }

    @PutMapping("/modify")
    public ResponseEntity<Void> modify(@RequestBody Memberaddress memberaddress) {
        memberAddressService.modify(memberaddress);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{addressIdx}")
    public ResponseEntity<Void> remove(@PathVariable("addressIdx") Long addressIdx) {
        memberAddressService.remove(addressIdx);
        return ResponseEntity.ok().build();
    }
}
