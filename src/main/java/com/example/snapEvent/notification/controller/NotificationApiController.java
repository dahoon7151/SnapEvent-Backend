package com.example.snapEvent.notification.controller;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.notification.dto.NotificationRequestDto;
import com.example.snapEvent.notification.service.NotificationService;
import com.example.snapEvent.notification.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
@Slf4j
public class NotificationApiController {

    /**
     *
     * 알림 설정(구독 엔티티에 대한 알림 설정)
     * 알림일 수정하기(구독한 브랜드 리스트 각각)
     * 알림 취소(구독한 브랜드 리스트 각각)
     */

    private final NotificationService notificationService;

    /**
     * 프론트에서 firebase를 시작하게 되면 알림을 허용하겠습니까? 라는 작은 팝업창이 뜬다.
     * @param token: 사용자가 허용을 누르면 토큰이 생성된다. 해당 토큰을 서버로 보낸다.
     * 서버는 토큰을 데이터베이스에 저장한다.
     */
    @PostMapping("/new")
    public void saveNotification(@RequestBody String token, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Member member = customUserDetail.getMember();
        log.info("사용자 : {}", member.getUsername());

        notificationService.saveNotification(token, member);
    }

    /*@PatchMapping("/{notificationId}/modify")
    public ResponseEntity<NotificationRequestDto> modifyNotification(@PathVariable Long notificationId, @AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody NotificationRequestDto req) {
        Member member = customUserDetail.getMember();
        log.info("사용자 : {}", member.getUsername());

        notificationService.modifyNotification(member, notificationId, req);

        return ResponseEntity.status(ok).body()

    }*/

    @DeleteMapping("/{notificationId}/delete")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Member member = customUserDetail.getMember();
        log.info("사용자 : {}", member.getUsername());

        notificationService.deleteNotification(member, notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("알림 삭제 완료");
    }
}
