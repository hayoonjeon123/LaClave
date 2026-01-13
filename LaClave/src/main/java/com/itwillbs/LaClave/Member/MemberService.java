package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder; 
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

	//로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 아이디입니다: " + username));
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getMemberId())   // 사용자 아이디
                .password(member.getMemberPw())   // DB에 저장된 '암호화된' 비밀번호
                .roles("USER")                    // 권한 설정 (ROLE_USER)
                .build();
    }
    
    // 아이디 비번찾기
    private Member getMemberIfValid(String memberId, String memberName, String email) {
        if (memberId == null) {
            // 아이디 찾기용 (이름 + 이메일)
            return memberRepository.findByMemberNameAndEmail(memberName, email)
                    .orElseThrow(() -> new RuntimeException("일치하는 정보가 없습니다."));
        } else {
            // 비밀번호 찾기용 (아이디 + 이름 + 이메일)
            return memberRepository.findByMemberIdAndMemberNameAndEmail(memberId, memberName, email)
                    .orElseThrow(() -> new RuntimeException("일치하는 정보가 없습니다."));
        }
    }

    // 2. 아이디 찾기 (재사용)
    public String findId(String memberName, String email) {
        return getMemberIfValid(null, memberName, email).getMemberId();
    }

    // 3. 비밀번호 찾기 (재사용)
    @Transactional
    public String findPw(String memberId, String memberName, String email) {
        Member member = getMemberIfValid(memberId, memberName, email);
        
        // 임시 비번 생성 및 저장 로직 진행
        String tempPw = UUID.randomUUID().toString().substring(0, 8);
        member.setMemberPw(passwordEncoder.encode(tempPw));
        return tempPw;
    }
    
    
	
}
