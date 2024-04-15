package com.example.snapevent.member.service;

import com.example.snapevent.member.dto.*;

public interface MemberService {
    public JwtToken login(LoginDto logInDto);

    public MemberDto join(JoinDto joinDto);

    public JwtToken reissue(ReissueDto reissueDto);

    public void logout(String username);

    public void withdraw(String username);

    public void modify(String username, ModifyDto modifyDto);
}