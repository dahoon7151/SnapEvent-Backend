package com.example.snapevent.member.dto;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
