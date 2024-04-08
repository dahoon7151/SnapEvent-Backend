package com.example.snapEvent.member.dto;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.member.entity.RefreshToken;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private String username;
    private String password;

    public RefreshToken toEntity(String username, String refreshToken) {

        return RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build();
    }
}

