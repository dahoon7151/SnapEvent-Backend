package com.example.snapEvent.notification.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class FcmMessageDto {

    // FCM에 실제 전송될 데이터의 Dto
    private boolean validateOnly;
    private FcmMessageDto.Message message;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Message {
        private FcmMessageDto.Notification notification;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Notification {
        private String title;
        private String body;
    }

}
