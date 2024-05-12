package com.example.snapEvent.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {

    private String title;
    private String body;

    @Builder(toBuilder = true)
    public FcmSendDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
