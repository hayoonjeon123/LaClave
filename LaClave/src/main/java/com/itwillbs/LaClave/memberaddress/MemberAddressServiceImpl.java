package com.itwillbs.LaClave.memberaddress;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberAddressServiceImpl implements MemberAddressService  {
	
	private final MemberAddressRepository memberAddressRepository;
	
	@Override
    public Integer register(Memberaddress memberaddress) {
		
		Memberaddress saved = memberAddressRepository.save(memberaddress);
		
        return saved.getAddressIdx();
    }

	@Override
	public Memberaddress get(Integer addressIdx) {
		
		return memberAddressRepository.findById(addressIdx).orElseThrow();
	}

	@Override
	public void modify(Memberaddress memberaddress) {
		
	}

	@Override
	public void remove(Integer addressIdx) {
		
	}
	
	
}
