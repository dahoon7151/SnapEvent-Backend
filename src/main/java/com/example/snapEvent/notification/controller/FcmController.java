package com.example.snapEvent.notification.controller;

import com.example.snapEvent.notification.common.codes.SuccessCode;
import com.example.snapEvent.notification.common.response.ApiResponseWrapper;
import com.example.snapEvent.notification.dto.FcmSendDto;
import com.example.snapEvent.notification.service.FcmService;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
@Slf4j
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponseWrapper<Object>> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다. ");
        int result = fcmService.sendMessageTo(fcmSendDto);

        ApiResponseWrapper<Object> arw = ApiResponseWrapper
                .builder()
                .result(result)
                .resultCode(SuccessCode.SELECT.getStatus())
                .resultMsg(SuccessCode.SELECT.getMessage())
                .build();
        return new ResponseEntity<>(arw, HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<String> getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/snapevent-push-firebase-adminsdk-kfsw3-daeb840e53.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));

        googleCredentials.refreshIfExpired();
        String accessToken = googleCredentials.getAccessToken().getTokenValue();

        return ResponseEntity.ok(accessToken);
    }
}
