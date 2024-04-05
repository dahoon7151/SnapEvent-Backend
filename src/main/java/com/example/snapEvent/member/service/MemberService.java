package com.example.snapEvent.member.service;

import com.example.snapEvent.common.dto.MemberDto;
import com.example.snapEvent.member.dto.JoinInDto;
import com.example.snapEvent.member.jwt.JwtToken;
import com.example.snapEvent.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface MemberService {
    public JwtToken logIn(String username, String password);
    public MemberDto join(JoinInDto joinInDto);
}
