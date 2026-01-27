package com.itwillbs.LaClave.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.itwillbs.LaClave.member.Member;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Long  getMemberIdx() {
        return member.getMemberIdx();
    }
    
    public Member getMember() {
        return member;
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }
    
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 지금은 권한 안 쓰니까
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() {
        return member.getMemberStatus() != null
            && member.getMemberStatus() == 1;
    }
}
