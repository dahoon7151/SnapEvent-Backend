package com.example.snapEvent.member.controller;

import com.example.snapEvent.member.dto.*;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
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

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
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
    public ResponseEntity<JwtToken> reissue(@RequestBody ReissueDto reissueDto,
                                            @AuthenticationPrincipal CustomUserDetail customUserDetail,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 이름 : {}", username);

        JwtToken jwtToken = memberService.reissue(request, username, reissueDto);
        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
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

    @PostMapping("/test")
    public String test() {
        return "토큰 테스트 성공";
    }
}