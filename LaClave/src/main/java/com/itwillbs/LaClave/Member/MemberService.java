package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	private final AiProfileRepository aiProfileRepository;

	// 회원 저장 (회원가입 시 사용)
	@Transactional
	public Member saveMember(Member member, MemberDTO dto) {
		// 1. 아이디 중복 체크 (아이디가 이미 있으면 예외 발생)
		memberRepository.findByMemberId(member.getMemberId()).ifPresent(m -> {
			throw new RuntimeException("이미 사용 중인 아이디입니다.");
		});

		if (member.getNickname() == null || member.getNickname().isEmpty()) {
			member.setNickname(generateRandomNickname());
		}

		// 2. 시스템 기본값 설정
		if (member.getMemberStatus() == null)
			member.setMemberStatus(1); // 1: 활성 상태
		if (member.getPoint() == null)
			member.setPoint(0);
		if (member.getMarketingAgree() == null)
			member.setMarketingAgree(0);
		if (member.getMailAuthStatus() == null)
			member.setMailAuthStatus(0);

		member.setMemberRole("ROLE_USER");
		member.setSignupDate(LocalDateTime.now());
		member.setUpdatedAt(LocalDateTime.now());

		// 3. 비밀번호 암호화
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));

		Member savedMember = memberRepository.save(member);

		// 3. AI 프로필 저장 로직 추가
		AiProfile aiProfile = new AiProfile();
		aiProfile.setMemberIdx(savedMember.getMemberIdx().longValue()); // 저장된 회원의 PK를 가져옴
		aiProfile.setHeight(dto.getHeight());
		aiProfile.setWeight(dto.getWeight());

		// 스타일 리스트가 있다면 문자열로 변환 (예: "Modern,Casual")
		if (dto.getPrefStyles() != null && !dto.getPrefStyles().isEmpty()) {
			String joinedStyles = String.join(",", dto.getPrefStyles());
			aiProfile.setPrefStyles(joinedStyles);
		}

		// aiProfileRepository를 통해 최종 저장
		aiProfileRepository.save(aiProfile);

		return savedMember;

	}

	// 로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByMemberId(username)
				.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 아이디입니다: " + username));

		return org.springframework.security.core.userdetails.User.builder().username(member.getMemberId()) // 사용자 아이디
				.password(member.getMemberPw()) // DB에 저장된 '암호화된' 비밀번호
				.roles("USER") // 권한 설정 (ROLE_USER)
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

	public MemberInfoResponse getMemberInfo(String memberId) {
		Member member = memberRepository.findByMemberId(memberId)
				.orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

		return MemberInfoResponse.builder()
				.memberName(member.getMemberName())
				.memberId(member.getMemberId())
				.email(member.getEmail())
				.postCode(member.getPostCode())
				.address(member.getAddress())
				.addressDetail(member.getAddressDetail())
				.point(member.getPoint())
				.build();
	}

	private String generateRandomNickname() {
		String[] adjectives = { "행복한", "즐거운", "용감한", "빠른", "빛나는" };
		String[] nouns = { "호랑이", "사자", "독수리", "고래", "거북이" };

		String adj = adjectives[(int) (Math.random() * adjectives.length)];
		String noun = nouns[(int) (Math.random() * nouns.length)];
		int randomNumber = (int) (Math.random() * 9999) + 1000; // 4자리 랜덤 숫자

		return adj + noun + randomNumber;
	}

}
