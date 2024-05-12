package com.example.snapEvent.member.controller;

import com.example.snapEvent.member.dto.*;
import com.example.snapEvent.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<JwtToken> reissue(@RequestBody ReissueDto reissueDto, HttpServletResponse response) {
        JwtToken jwtToken = memberService.reissue(reissueDto);
        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }


    @DeleteMapping("/logout/{username}")
    public ResponseEntity<String> logout(
            @PathVariable(value = "username") String username) {
        log.info("유저 이름 : {}", username);
        memberService.logout(username);

        // * 로그아웃 시

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }

    @DeleteMapping("/withdraw/{username}")
    public ResponseEntity<String> withdraw(
            @PathVariable(value = "username") String username) {
        log.info("탈퇴 유저 이름 : {}", username);
        memberService.withdraw(username);

        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴 성공");
    }

    @PatchMapping("/modify/{username}")
    public ResponseEntity<ModifyResponseDto> modify(
            @PathVariable(value = "username") String username, @RequestBody @Valid ModifyDto modifyDto) {
        ModifyResponseDto modifyResponseDto = memberService.modify(username, modifyDto);

        return ResponseEntity.status(HttpStatus.OK).body(modifyResponseDto);
    }


    @PostMapping("/test")
    public String test() {
        return "토큰 테스트 성공";
    }
}