package com.example.snapEvent.member.dto;

import com.example.snapEvent.member.entity.RefreshToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ReissueDto {
    private final String accessToken;
    private final String refreshToken;

    public RefreshToken toEntity(String username, String refreshToken) {

        return RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build();
    }
}
