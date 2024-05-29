package com.example.snapEvent.member.service;

import com.example.snapEvent.member.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {
    public JwtToken login(LoginDto logInDto);

    public MemberDto join(JoinDto joinDto);

    public JwtToken reissue(String token);

    public boolean logout(String username);

    public boolean withdraw(String username);

    public ModifyResponseDto modify(String username, ModifyDto modifyDto);

    public boolean checkNickname(CheckNameDto checkNameDto);
}