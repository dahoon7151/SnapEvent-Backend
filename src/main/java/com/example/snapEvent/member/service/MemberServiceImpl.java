package com.example.snapEvent.member.service;

import com.example.snapEvent.follow.service.FollowService;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.dto.*;
import com.example.snapEvent.member.entity.RefreshToken;
import com.example.snapEvent.member.security.jwt.CustomUserDetailsService;
import com.example.snapEvent.member.security.jwt.JwtTokenProvider;
import com.example.snapEvent.member.repository.MemberRepository;
import com.example.snapEvent.member.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private final FollowService followService;
    private final CustomUserDetailsService customUserDetailsService;

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
    public JwtToken reissue(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new IllegalArgumentException("DB에 저장되어 있는 refreshToken과 다릅니다. 다시 로그인 해주세요."));

        String username = refreshToken.getUsername();

        // 리프레시 토큰을 검증한 후 토큰 재발급
        if (jwtTokenProvider.validateToken(token)) {
            log.info("refresh Token 검증 완료");

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            log.info("재발급 토큰 생성");

            refreshTokenRepository.save(RefreshToken.builder()
                    .username(username)
                    .refreshToken(token)
                    .build());

            return jwtToken;
        }
        throw new IllegalArgumentException("비정상 에러");
    }

    @Transactional
    @Override
    public boolean logout(String username) {
        refreshTokenRepository.delete(refreshTokenRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username).")));
        return true;
    }

    @Transactional
    @Override
    public boolean withdraw(String username) {
        refreshTokenRepository.delete(refreshTokenRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username).")));
        log.info("refresh 토큰 삭제");

        Member delmember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username)."));

        //팔로우 관계 삭제
        followService.deleteFollowRelation(delmember.getId());
        log.info("팔로우 관계 삭제");
        //DB 삭제
        memberRepository.delete(delmember);
        log.info("회원 정보 삭제");

        return true;
    }

    @Transactional
    @Override
    public ModifyResponseDto modify(String username, ModifyDto modifyDto) {
        if(!modifyDto.getPassword().equals(modifyDto.getCheckPassword())){
            throw new IllegalArgumentException("비밀번호가 재입력된 비밀번호와 동일하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(modifyDto.getPassword());
        Member member = memberRepository.findByUsername(username)
                .map(entity -> entity.update(encodedPassword, modifyDto.getNickname()))
                .orElseThrow(() -> new UsernameNotFoundException("비정상 에러(incorrect username)."));

        Member modifiedMember = memberRepository.save(member);

        return new ModifyResponseDto(modifiedMember);
    }

    @Transactional
    @Override
    public String checkNickname(CheckNameDto checkNameDto) {
        String nickname = checkNameDto.getNickname();

        if (memberRepository.existsByNickname(nickname)) {
            return "이미 사용중인 닉네임입니다.";
        } else {
            return "사용 가능한 닉네임입니다.";
        }
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
