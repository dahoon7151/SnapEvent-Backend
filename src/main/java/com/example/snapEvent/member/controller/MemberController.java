package com.example.snapEvent.member.controller;

import com.example.snapEvent.common.dto.MemberDto;
import com.example.snapEvent.member.dto.JoinInDto;
import com.example.snapEvent.member.dto.LogInDto;
import com.example.snapEvent.member.jwt.JwtToken;
import com.example.snapEvent.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "회원관리 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "JWT 로그인", description = "아이디,비밀번호를 입력받아 검증하고 토큰을 발급하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "잘못된 회원정보", content = @Content(mediaType = "application/json"))
                    })
    @Parameters({
            @Parameter(name = "username", description = "아이디", example = "dahoon1234"),
            @Parameter(name = "password", description = "비밀번호", example = "abcd1234")
    })
    public JwtToken logIn(@RequestBody LogInDto logInDto) {
        String username = logInDto.getUsername();
        String password = logInDto.getPassword();
        JwtToken jwtToken = memberService.logIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDto> join(@RequestBody JoinInDto joinInDto) {
        MemberDto savedMemberDto = memberService.join(joinInDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @Operation(summary = "JWT 검증 테스트", description = "로그인 성공시 발급된 토큰을 검증하기 위한 테스트 API")
    @Parameters({
            @Parameter(name = "grantType", description = "인증 타입", example = "bearer"),
            @Parameter(name = "accessToken")
    })
    @PostMapping("/test")
    public String test() {
        return "success";
    }
}