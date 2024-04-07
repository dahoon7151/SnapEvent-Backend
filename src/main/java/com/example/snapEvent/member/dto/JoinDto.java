package com.example.snapEvent.member.dto;

import com.example.snapEvent.common.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {

    @NotBlank(message = "아이디는 공백을 포함할 수 없습니다.")
    private String username;

    @NotNull(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}",
            message = "비밀번호는 영어 대/소문자, 숫자, 특수기호가 최소한 1개씩은 포함이 되어있어야 하며, 5~20글자 이내여야 합니다.")
    private String password;

    @NotNull(message = "비밀번호는 공백일 수 없습니다.")
    private String checkPassword;

    @Size(max = 20, min = 1, message = "닉네임은 20자 이하로 가능합니다.")
    private String nickname;

    private List<String> roles = new ArrayList<>();

    public Member toEntity(String encodedPassword, List<String> roles) {

        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .roles(roles)
                .build();
    }
}
