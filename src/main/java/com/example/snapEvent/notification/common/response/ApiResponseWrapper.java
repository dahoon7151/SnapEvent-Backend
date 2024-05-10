package com.example.snapEvent.notification.common.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ApiResponseWrapper<T> {

    // API 응답 결과 Response
    private T result;

    // API 응답 코드 Response
    private int resultCode;

    // API 응답 코드 Message
    private String resultMsg;
}
