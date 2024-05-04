package com.example.snapEvent.member.security;

import com.example.snapEvent.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CustomUserDetail implements UserDetails, OAuth2User {
    private UserDetails user;
    private Map<String,Object> attributes;

    //생성자 직접 선언함 USER->MEMBER

    //일반 로그인
    public CustomUserDetail(UserDetails user) {
        this.user = user;
    }

    //OAuth 로그인
    public CustomUserDetail(UserDetails user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
    @Override
    public String getName() {         // 안 씀
        return null;
    }

    // OAuth2용 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // Security 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
