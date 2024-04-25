package com.example.snapEvent.member.dto;

import com.example.snapEvent.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyResponseDto {
    private String modifiedPassword;
    private String modifiedNickname;

    public ModifyResponseDto(Member member) {
        this.modifiedPassword = member.getPassword();
        this.modifiedNickname = member.getNickname();
    }
}
