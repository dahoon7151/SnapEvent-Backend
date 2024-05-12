package com.example.snapEvent.notification.service;

import com.example.snapEvent.notification.dto.FcmMessageDto;
import com.example.snapEvent.notification.dto.FcmSendDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FcmServiceImpl implements FcmService{
    @Override
    public int sendMessageTo(FcmSendDto fcmSendDto) throws IOException {
        String message = makeMessage(fcmSendDto);
        // RestTemplate을 활용하여 동기적으로 메시지 보냄
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        String API_URL = "<https://fcm.googleapis.com/v1/projects/snapevent-push/messages:send>";
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        log.debug("상태코드: {}", response.getStatusCode());

        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
     * OAuth2.0 토큰 방식을 사용하여 토큰 탈취 방지
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/snapevent-push-firebase-adminsdk-kfsw3-daeb840e53.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .notification(FcmMessageDto.Notification.builder()
                                .title(fcmSendDto.getTitle())
                                .body(fcmSendDto.getBody())
                                .build()
                        ).build()).validateOnly(false).build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
