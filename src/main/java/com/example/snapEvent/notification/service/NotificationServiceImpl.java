package com.example.snapEvent.notification.service;

import com.example.snapEvent.exception.UserException;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.notification.dto.NotificationRequestDto;
import com.example.snapEvent.notification.entity.Notification;
import com.example.snapEvent.notification.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    // * 구독 리스트 각각에 대한 알림 생성 필요
    @Override
    public void saveNotification(String token, Member member) {
        Notification notification = Notification.builder()
                .token(token)
                .build();

        notification.confirmMember(member);
        notificationRepository.save(notification);
    }

    @Override
    public String getNotificationToken() {
        //? @AuthenticationPrincipal은 내부적으로 UserDetailsService를 이용해서 DB로부터 유저 객체를 조회 후에 가져오기 때문에 조회 쿼리가 발생, 따라서 서비스 계층에서는 SecurityContextHolder로 로그인 객체를 직접 조회하였음
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetail customUserDetail = (CustomUserDetail) principal;
        Member member = customUserDetail.getMember();
        Notification notification = notificationRepository.findByMember(member).orElseThrow(UserException::new);

        return notification.getToken();
    }

    // * firebase한테 보내는 서비스 로직
    @Override
    public void sendNotification(NotificationRequestDto req) throws ExecutionException, InterruptedException {

        // 로그인한 객체로부터 notificationRepository.findByToken(member)를 어떻게 하지?
        String notificationToken = getNotificationToken();

        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle(req.getTitle())
                                .setBody(req.getMessage())
                                .build())
                        .build())
                .setToken(notificationToken)
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        log.info(">>>>Send message : {}", response);
    }

    // * 알림 취소
    @Override
    public void deleteNotification(Member member, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 알림이 없습니다."));
        notificationRepository.delete(notification);
    }

    // * 마찬가지로 구독 리스트 각각에 대한 알림일 수정 필요 -> 구글 캘린더 API 필요
    @Override
    public void modifyNotification(Member member, Long notificationId, NotificationRequestDto req) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 알림이 없습니다."));
    }

}
