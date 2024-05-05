package com.example.snapEvent.subscribe.controller;

import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.subscribe.dto.SubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subs")
public class SubscriptionController {
    @PostMapping("/subscribe")
    public ResponseEntity<SubscribeResponseDto> subscribe(@AuthenticationPrincipal CustomUserDetail customUserDetail) {

    }
}
