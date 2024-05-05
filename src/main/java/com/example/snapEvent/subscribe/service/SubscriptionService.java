package com.example.snapEvent.subscribe.service;

import com.example.snapEvent.subscribe.dto.SubscribeDto;
import com.example.snapEvent.subscribe.dto.SubscribeResponseDto;

import java.util.List;

public interface SubscriptionService {
    // 온보딩 페이지에서 구독
    public List<SubscribeResponseDto> subscribe(String username, SubscribeDto subscribeDto);

    // 구독 리스트 조회
    public List<SubscribeResponseDto> showSubList(String username);
}
