package com.itwillbs.LaClave.memberaddress;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Config.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberAddressServiceImpl implements MemberAddressService  {
	
	private final MemberAddressRepository memberAddressRepository;
	
	
	
    @Override
    public Long register(Memberaddress memberaddress, Long memberIdx) {
        // 로그인한 회원 idx를 엔티티에 설정
        memberaddress.setMemberIdx(memberIdx);

        // 저장
        Memberaddress saved = memberAddressRepository.save(memberaddress);

        // 저장된 주소 idx 반환
        return saved.getAddressIdx();
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<MemberAddressDto> getMyAddressList(CustomUserDetails user) {
	    // 로그인한 회원의 주소만 조회
	    List<Memberaddress> addresses = memberAddressRepository.findByMemberIdxOrderByAddressIdxDesc(user.getMemberIdx());

	    // DTO로 변환
	    return addresses.stream()
	            .map(a -> new MemberAddressDto(
	                    a.getAddressIdx(),
	                    a.getRecipientName(),
	                    a.getAddressName(),
	                    a.getPhone(),
	                    a.getPostCode(),
	                    a.getAddress(),
	                    a.getAddressDetail()
	            ))
	            .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public MemberAddressDto get(Long addressIdx, CustomUserDetails user) {
	    Memberaddress address = memberAddressRepository
	            .findByAddressIdxAndMemberIdx(addressIdx, user.getMemberIdx())
	            .orElseThrow(() -> new RuntimeException("해당 회원의 주소가 존재하지 않습니다."));

	    return new MemberAddressDto(
	            address.getAddressIdx(),
	            address.getRecipientName(),
	            address.getAddressName(),
	            address.getPhone(),
	            address.getPostCode(),
	            address.getAddress(),
	            address.getAddressDetail()
	    );
	}

	@Override
	public void modify(Memberaddress updatedAddress, CustomUserDetails user) {
	    Memberaddress existing = memberAddressRepository
	            .findByAddressIdxAndMemberIdx(updatedAddress.getAddressIdx(), user.getMemberIdx())
	            .orElseThrow(() -> new RuntimeException("해당 회원의 주소가 존재하지 않습니다."));

	    // 필요한 필드만 업데이트
	    existing.setRecipientName(updatedAddress.getRecipientName());
	    existing.setAddressName(updatedAddress.getAddressName());
	    existing.setPhone(updatedAddress.getPhone());
	    existing.setPostCode(updatedAddress.getPostCode());
	    existing.setAddress(updatedAddress.getAddress());
	    existing.setAddressDetail(updatedAddress.getAddressDetail());

	    memberAddressRepository.save(existing); // JPA는 사실 수정된 엔티티 자동 반영되지만, 명시적으로 save해도 ok
	}


	@Override
	public void remove(Long addressIdx, CustomUserDetails user) {
	    int deletedCount = memberAddressRepository
	            .deleteByAddressIdxAndMemberIdx(addressIdx, user.getMemberIdx());

	    if (deletedCount == 0) {
	        throw new RuntimeException("삭제할 주소가 없거나 권한이 없습니다.");
	    }
	}
	
	
}
