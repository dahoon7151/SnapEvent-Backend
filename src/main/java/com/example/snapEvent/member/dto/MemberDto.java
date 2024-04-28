package com.example.snapEvent.member.dto;

import com.example.snapEvent.member.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String nickname;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }
}
