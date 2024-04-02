package com.example.snapEvent.jwt;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
