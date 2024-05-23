package com.example.snapEvent.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseWrapper<T> {

    // API 응답 코드 Response
    private int resultCode;

    // API 응답 코드 Message
    private String resultMsg;

    @Builder
    public ApiResponseWrapper(final int resultCode, final String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
