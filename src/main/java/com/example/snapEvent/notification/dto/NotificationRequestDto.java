package com.example.snapEvent.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRequestDto {

    private String title;
    private String message;
    private String token;

    @Builder
    public NotificationRequestDto(String title, String message, String token) {
        this.title = title;
        this.message = message;
        this.token = token;
    }
}
