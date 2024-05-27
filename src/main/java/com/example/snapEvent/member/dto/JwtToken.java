package com.example.snapEvent.member.dto;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public JwtToken hideRT(JwtToken jwtToken){
        return JwtToken.builder()
                .grantType(jwtToken.grantType)
                .accessToken(jwtToken.accessToken)
                .build();
    }
}
