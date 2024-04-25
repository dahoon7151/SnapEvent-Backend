package com.example.snapEvent.member.security.jwt;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.repository.MemberRepository;
import com.example.snapEvent.member.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
        return new CustomUserDetail(member);
    }

    // 해당하는 Member 의 데이터가 존재한다면 Member 객체로 만들어서 return
    private Member createUserDetails(Member member) {
        return Member.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(List.of(member.getRoles().toArray(new String[0])))
                .build();
    }
}