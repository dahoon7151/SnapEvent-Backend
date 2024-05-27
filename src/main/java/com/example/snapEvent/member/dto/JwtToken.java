package com.example.snapEvent.member.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public JwtToken hideRT(JwtToken jwtToken){
        log.info("refreshToken 숨기기");
        return JwtToken.builder()
                .grantType(jwtToken.grantType)
                .accessToken(jwtToken.accessToken)
                .build();
    }
}
