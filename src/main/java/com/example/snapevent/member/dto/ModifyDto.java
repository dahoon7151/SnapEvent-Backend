package com.example.snapevent.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyDto {
    @NotNull(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}",
            message = "비밀번호는 영어 대/소문자, 숫자, 특수기호가 최소한 1개씩은 포함이 되어있어야 하며, 5~20글자 이내여야 합니다.")
    private String password;

    @NotNull(message = "비밀번호는 공백일 수 없습니다.")
    private String checkPassword;

    @Size(max = 20, min = 1, message = "닉네임은 20자 이하로 가능합니다.")
    private String nickname;
}
