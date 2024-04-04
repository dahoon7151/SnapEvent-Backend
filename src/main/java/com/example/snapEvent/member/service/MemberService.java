package com.example.snapEvent.member.service;

import com.example.snapEvent.member.jwt.JwtToken;

public interface MemberService {
    public JwtToken signIn(String username, String password);
}
