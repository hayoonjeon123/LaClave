package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder; // 암호화를 위해 추가
	
	// 회원 저장 (회원가입 시 사용)
	@Transactional
    public Member saveMember(Member member) {
        // 1. 아이디 중복 체크 (아이디가 이미 있으면 예외 발생)
        memberRepository.findByMemberId(member.getMemberId())
                .ifPresent(m -> {
                    throw new RuntimeException("이미 사용 중인 아이디입니다.");
                });

        // 2. 시스템 기본값 설정
        if (member.getMemberStatus() == null) member.setMemberStatus(1); // 1: 활성 상태
        if (member.getPoint() == null) member.setPoint(0);
        if (member.getMarketingAgree() == null) member.setMarketingAgree(0);
        if (member.getMailAuthStatus() == null) member.setMailAuthStatus(0);
        
        member.setMemberRole("ROLE_USER");
        member.setSignupDate(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        // 3. 비밀번호 암호화
        member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));

        return memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 아이디입니다: " + username));
    }
	
}
