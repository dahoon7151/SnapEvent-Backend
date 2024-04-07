package com.example.snapEvent.member.service;

import com.example.snapEvent.common.dto.MemberDto;
import com.example.snapEvent.member.dto.JoinDto;
import com.example.snapEvent.member.dto.LogInDto;
import com.example.snapEvent.member.jwt.JwtToken;

public interface MemberService {
    public JwtToken logIn(LogInDto logInDto);

    public MemberDto join(JoinDto joinDto);
}
