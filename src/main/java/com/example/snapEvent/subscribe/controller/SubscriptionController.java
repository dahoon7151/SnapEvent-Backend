package com.example.snapEvent.subscribe.controller;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.subscribe.SiteName;
import com.example.snapEvent.subscribe.dto.SubscribeDto;
import com.example.snapEvent.subscribe.dto.SubscribeResponseDto;
import com.example.snapEvent.subscribe.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subs")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<List<SubscribeResponseDto>> subscribe(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @RequestBody SubscribeDto subscribeDto) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        List<SubscribeResponseDto> subscribeResponseDto = subscriptionService.subscribe(username, subscribeDto);

        return ResponseEntity.status(HttpStatus.OK).body(subscribeResponseDto);
    }

    @GetMapping("/showlist")
    public ResponseEntity<List<SubscribeResponseDto>> showList(
            @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        List<SubscribeResponseDto> subscribeResponseDto = subscriptionService.showSubList(username);

        return ResponseEntity.status(HttpStatus.OK).body(subscribeResponseDto);
    }

    @DeleteMapping("/cancel/{sitename}")
    public ResponseEntity<String> cancel(@PathVariable(value = "sitename") String site,
                                         @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        SiteName siteName = SiteName.valueOf(site);
        log.info("사이트명 Enum 변환");
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        subscriptionService.cancelSubscription(username, siteName);

        return ResponseEntity.status(HttpStatus.OK).body("구독 취소 완료");
    }
}
