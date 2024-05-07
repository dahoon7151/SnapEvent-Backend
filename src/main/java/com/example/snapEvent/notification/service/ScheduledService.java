package com.example.snapEvent.notification.service;

import com.example.snapEvent.notification.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
// 구글 캘린더 API로 대체될 예정
public class ScheduledService {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 30 7 * * ?")
    public void scheduledSend() throws ExecutionException, InterruptedException {
        NotificationRequestDto notificationRequestDto = NotificationRequestDto.builder()
                .title("snapEvent")
                .token(notificationService.getNotificationToken())
                .message("테스트 용도입니다.")
                .build();
        notificationService.sendNotification(notificationRequestDto);
        log.info("보내졌습니다!");
    }
}
