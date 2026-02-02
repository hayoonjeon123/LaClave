package com.itwillbs.LaClave.member;

import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;
import com.itwillbs.LaClave.commoncode.CommonCodeService; // This might be needed if I see it used elsewhere, but let's stick to what's there

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class MemberService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;
	private final AiProfileRepository aiProfileRepository;
	private final MemberAddressRepository memberAddressRepository;

	// 1. 중복 체크 공통 로직
	public boolean existsByMemberId(String memberId) {
		return memberRepository.existsByMemberId(memberId);
	}

	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	// 2. 통합 회원가입 로직 (중복 체크 포함)
	@Transactional
	public void registerNewMember(MemberDTO dto) {
		if (existsByMemberId(dto.getMemberId())) {
			throw new RuntimeException("이미 사용 중인 아이디입니다.");
		}
		if (existsByEmail(dto.getEmail())) {
			throw new RuntimeException("이미 사용 중인 이메일입니다.");
		}

		Member member = Member.builder()
				.memberId(dto.getMemberId())
				.memberPw(passwordEncoder.encode(dto.getMemberPw())) // 암호화 포함
				.memberName(dto.getMemberName())
				.email(dto.getEmail())
				.gender(dto.getGender())
				.birth(dto.getBirth())
				.postCode(dto.getPostCode())
				.address(dto.getAddress())
				.addressDetail(dto.getAddressDetail())
				.marketingAgree(dto.getMarketingAgree())
				.memberStatus(3)
				.point(0)
				.mailAuthStatus(1) // 이메일 인증 통과 상태로 가입
				.nickname(generateRandomNickname())
				.signupDate(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.memberRole("ROLE_USER")
				.build();

		Member savedMember = memberRepository.save(member);

		try {
			Memberaddress defaultAddr = Memberaddress.builder()
					.memberIdx(savedMember.getMemberIdx())
					.recipientName(savedMember.getMemberName())
					.addressName("기본 배송지")
					.phone("010-0000-0000")
					.postCode(savedMember.getPostCode())
					.address(savedMember.getAddress())
					.addressDetail(savedMember.getAddressDetail())
					.build();
			memberAddressRepository.save(defaultAddr);
		} catch (Exception e) {
			throw new RuntimeException("배송지 정보를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
		}

		// AI 정보 저장
		AiProfile aiProfile = new AiProfile();
		aiProfile.setMemberIdx(savedMember.getMemberIdx());
		aiProfile.setHeight(dto.getHeight() != null ? dto.getHeight() : 0.0);
		aiProfile.setWeight(dto.getWeight() != null ? dto.getWeight() : 0.0);

		if (dto.getPrefStyles() != null && !dto.getPrefStyles().isEmpty()) {
			aiProfile.setPrefStyles(String.join(",", dto.getPrefStyles()));
		}
		aiProfileRepository.save(aiProfile);
	}

	// 아이디 비번찾기
	public Member getMemberIfValid(String memberId, String memberName, String email) {
		if (memberId == null || memberId.trim().isEmpty()) {
			return memberRepository.findByMemberNameAndEmail(memberName, email)
					.orElseThrow(() -> new RuntimeException("일치하는 정보가 없습니다."));
		} else {
			return memberRepository.findByMemberIdAndMemberNameAndEmail(memberId, memberName, email)
					.orElseThrow(() -> new RuntimeException("일치하는 정보가 없습니다."));
		}
	}

	// 이메일 발송 전 회원 존재 여부 확인
	public void validateMemberForEmail(MemberDTO dto) {
		getMemberIfValid(dto.getMemberId(), dto.getMemberName(), dto.getEmail());
	}

	// 아이디 찾기
	public String findId(String memberName, String email) {
		log.info("아이디 찾기 시도: name={}, email={}", memberName, email);
		return getMemberIfValid(null, memberName.trim(), email.trim()).getMemberId();
	}

	// 비밀번호 찾기
	@Transactional
	public String findPw(String memberId, String memberName, String email) {
		Member member = getMemberIfValid(memberId.trim(), memberName.trim(), email.trim());

		String tempPw = UUID.randomUUID().toString().substring(0, 8);
		member.setMemberPw(passwordEncoder.encode(tempPw));
		return tempPw;
	}

	public MemberInfoResponse getMemberInfo(String memberId) {
		Member member = memberRepository.findByMemberId(memberId)
				.orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

		Memberaddress latestAddr = memberAddressRepository.findByMemberIdxOrderByAddressIdxDesc(member.getMemberIdx())
				.stream().findFirst().orElse(null);

		return MemberInfoResponse.builder()
				.memberName(member.getMemberName())
				.memberId(member.getMemberId())
				.nickname(member.getNickname())
				.birth(member.getBirth())
				.email(member.getEmail())
				.postCode(member.getPostCode())
				.address(member.getAddress())
				.addressDetail(member.getAddressDetail())
				.point(member.getPoint())
				.phone(latestAddr != null ? latestAddr.getPhone() : "010-0000-0000")
				.addrIdx(latestAddr != null ? latestAddr.getAddressIdx() : null)
				.build();
	}

	private String generateRandomNickname() {
		String[] adjectives = { "행복한", "즐거운", "용감한", "빠른", "빛나는" };
		String[] nouns = { "호랑이", "사자", "독수리", "고래", "거북이" };

		String adj = adjectives[(int) (Math.random() * adjectives.length)];
		String noun = nouns[(int) (Math.random() * nouns.length)];
		int randomNumber = (int) (Math.random() * 9999) + 1000;

		return adj + noun + randomNumber;
	}

	// 회원정보 수정
	@Transactional
	public void updateMemberInfo(Long memberIdx, MemberUpdateDto dto) {
		Member member = memberRepository.findById(memberIdx)
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		if (dto.getMemberName() != null)
			member.setMemberName(dto.getMemberName());
		if (dto.getNickname() != null)
			member.setNickname(dto.getNickname());
		if (dto.getBirth() != null)
			member.setBirth(dto.getBirth());
		if (dto.getPostCode() != null)
			member.setPostCode(dto.getPostCode());
		if (dto.getAddress() != null)
			member.setAddress(dto.getAddress());
		if (dto.getAddressDetail() != null)
			member.setAddressDetail(dto.getAddressDetail());

		member.setUpdatedAt(LocalDateTime.now());

		memberRepository.save(member);
	}

	// 비밀번호 수정
	@Transactional
	public void updatePassword(Long memberIdx, PasswordUpdateDto dto) {
		Member member = memberRepository.findById(memberIdx)
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		if (!passwordEncoder.matches(dto.getCurrentPassword(), member.getMemberPw())) {
			throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
		}

		member.setMemberPw(passwordEncoder.encode(dto.getNewPassword()));
		member.setUpdatedAt(LocalDateTime.now());

		memberRepository.save(member);
	}

	@Transactional
	public void withdrawMemberWithPassword(Long memberIdx, String password) {

		Member member = memberRepository.findById(memberIdx)
				.orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));

		if (member.getMemberStatus() != null && member.getMemberStatus() == 2) {
			throw new RuntimeException("이미 탈퇴한 회원입니다.");
		}

		if (!passwordEncoder.matches(password, member.getMemberPw())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		// 탈퇴 처리
		member.setMemberStatus(2);
		member.setUpdatedAt(LocalDateTime.now());
		SecurityContextHolder.clearContext();
	}

}
