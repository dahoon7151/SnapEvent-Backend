package com.example.snapEvent.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {

    private String token; // 디바이스로부터 발급받은 FCM 토큰
    private String title;
    private String body;

    @Builder(toBuilder = true)
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
