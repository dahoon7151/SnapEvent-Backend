package com.example.snapEvent.member.controller;

import com.example.snapEvent.member.dto.*;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        log.info("로그인 - 아이디 : {}, 비밀번호 : {}",
                loginDto.getUsername(),
                loginDto.getPassword()
        );
        JwtToken jwtToken = memberService.login(loginDto);
        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());

        JwtToken tokenResponse = jwtToken.hideRT(jwtToken);
        Cookie cookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        cookie.setDomain("snapevent.site");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDto> join(@RequestBody @Valid JoinDto joinDto) {
        log.info("회원가입 - 아이디 : {}, 비밀번호 : {}, 재확인 비밀번호 : {}, 닉네임 : {}",
                joinDto.getUsername(),
                joinDto.getPassword(),
                joinDto.getCheckPassword(),
                joinDto.getNickname()
        );
        MemberDto savedMemberDto = memberService.join(joinDto);

        return ResponseEntity.status(HttpStatus.OK).body(savedMemberDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtToken> reissue(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("refreshToken");
        if (token==null) {
            throw new IllegalArgumentException("쿠키에서 토큰을 조회하지 못했습니다.");
        }
        log.info("쿠키에서 refresh 토큰 추출");
        JwtToken jwtToken = memberService.reissue(token);
        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());

        JwtToken tokenResponse = jwtToken.hideRT(jwtToken);
        Cookie cookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        cookie.setDomain("snapevent.site");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }


    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 이름 : {}", username);
        memberService.logout(username);

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("탈퇴 유저 이름 : {}", username);
        memberService.withdraw(username);

        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴 성공");
    }

    @PatchMapping("/modify")
    public ResponseEntity<ModifyResponseDto> modify(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @RequestBody @Valid ModifyDto modifyDto) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 이름 : {}", username);
        ModifyResponseDto modifyResponseDto = memberService.modify(username, modifyDto);

        return ResponseEntity.status(HttpStatus.OK).body(modifyResponseDto);
    }

    @GetMapping("/checkname")
    public ResponseEntity<String> checkName(@RequestBody CheckNameDto checkNameDto) {
        String checkRedundant = memberService.checkNickname(checkNameDto);

        return ResponseEntity.status(HttpStatus.OK).body(checkRedundant);
    }

    @PostMapping("/test")
    public String test() {
        return "토큰 테스트 성공";
    }

//    @GetMapping("/social/{registerId}")
//    public String socialLogin(@PathVariable(value = "registerId") String social) {
//        String apiUrl = "";
//        if (social.equals("google")){
//            apiUrl = "https://snapevent.site/oauth2/authorization/google";
//        } else if (social.equals("naver")) {
//            apiUrl = "https://snapevent.site/oauth2/authorization/naver";
//        } else if (social.equals("kakao")) {
//            apiUrl = "https://snapevent.site/oauth2/authorization/kakao";
//        }
//
//        return restTemplate.getForObject(apiUrl, String.class);
//    }
}