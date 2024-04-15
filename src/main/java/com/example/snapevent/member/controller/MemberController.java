package com.example.snapevent.member.controller;

import com.example.snapevent.member.dto.*;
import com.example.snapevent.member.service.MemberService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/members")
//@Tag(name = "회원관리 API")
public class MemberController {

    private final MemberService memberService;


//    @Operation(summary = "JWT 로그인", description = "아이디,비밀번호를 입력받아 검증하고 토큰을 발급하는 API")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "403", description = "잘못된 회원정보", content = @Content(mediaType = "application/json"))
//                    })
//    @Parameters({
//            @Parameter(name = "username", description = "아이디", example = "dahoon1234"),
//            @Parameter(name = "password", description = "비밀번호", example = "abcd1234")
//    })
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

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }

    @DeleteMapping("/withdraw/{username}")
    public ResponseEntity<String> withdraw(
            @PathVariable(value = "username") String username) {
        log.info("탈퇴 유저 이름 : {}", username);
        memberService.withdraw(username);

        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴 성공");
    }

//    @Operation(summary = "JWT 검증 테스트", description = "로그인 성공시 발급된 토큰을 검증하기 위한 테스트 API")
//    @Parameters({
//            @Parameter(name = "grantType", description = "인증 타입", example = "bearer"),
//            @Parameter(name = "accessToken")
//    })
    @PostMapping("/test")
    public String test() {
        return "토큰 테스트 성공";
    }
}