package com.example.snapevent.member.service;

import com.example.snapevent.common.entity.Member;
import com.example.snapevent.member.dto.*;
import com.example.snapevent.member.entity.RefreshToken;
import com.example.snapevent.member.jwt.JwtTokenProvider;
import com.example.snapevent.member.repository.MemberRepository;
import com.example.snapevent.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtToken login(LoginDto logInDto) {
        String username = logInDto.getUsername();
        String password = logInDto.getPassword();
        log.info("request username = {}, password = {}", username, password);

        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        // db에 리프레시 토큰 저장
        refreshTokenRepository.save(logInDto.toEntity(username, jwtToken.getRefreshToken()));

        return jwtToken;
    }

    @Transactional
    @Override
    public MemberDto join(JoinDto joinDto) {
        if (memberRepository.existsByUsername(joinDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        if(!joinDto.getPassword().equals(joinDto.getCheckPassword())){
            throw new IllegalArgumentException("비밀번호가 재입력된 비밀번호와 동일하지 않습니다.");
        }

        // Password 암호화
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return MemberDto.toDto(memberRepository.save(joinDto.toEntity(encodedPassword, roles)));
    }

    @Transactional
    @Override
    public JwtToken reissue(ReissueDto reissueDto) {
        String username = reissueDto.getUsername();
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원의 refresh Token을 찾을 수 없습니다."));

        if (!reissueDto.getRefreshToken().equals(refreshToken.getRefreshToken())) {
            throw new IllegalArgumentException("DB에 저장되어 있는 refreshToken과 다릅니다. 다시 로그인 해주세요.");
        }
        // 리프레시 토큰을 검증한 후 토큰 재발급
        if (jwtTokenProvider.validateToken(reissueDto.getRefreshToken())) {
            Authentication authentication = jwtTokenProvider.getAuthentication(reissueDto.getRefreshToken());
            return jwtTokenProvider.generateToken(authentication);
        }
        throw new IllegalArgumentException("비정상 에러");
    }

    @Transactional
    @Override
    public void logout(String username) {
        refreshTokenRepository.delete(refreshTokenRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username).")));
    }

    @Transactional
    @Override
    public void withdraw(String username) {
        refreshTokenRepository.delete(refreshTokenRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username).")));
        //DB 삭제
        memberRepository.delete(memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username).")));
    }

    @Transactional
    @Override
    public void modify(String username, ModifyDto modifyDto) {
        if(!modifyDto.getPassword().equals(modifyDto.getCheckPassword())){
            throw new IllegalArgumentException("비밀번호가 재입력된 비밀번호와 동일하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(modifyDto.getPassword());
        Member member = memberRepository.findByUsername(username)
                .map(entity -> entity.update(encodedPassword, modifyDto.getNickname()))
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username)."));

        memberRepository.save(member);
    }
}
