package com.itwillbs.LaClave.Member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
    // 회원 저장
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
	
}
