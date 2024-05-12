package com.example.snapEvent.notification.service;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.notification.dto.FcmSendDto;
import com.example.snapEvent.notification.entity.Notification;
import com.example.snapEvent.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    // 웹에서 사용되는 알림 서비스

    private final NotificationRepository notificationRepository;

    // * 로그인 정보를 가져와서 사이트 내의 이벤트 별로 알림 설정할 것
    @Override
    public void saveNotification(String token, Member member) {
        Notification notification = Notification.builder()
                .token(token)
                .build();

        notification.confirmMember(member);
        notificationRepository.save(notification);
    }

    // * 알림 취소
    @Override
    public void deleteNotification(Member member, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 알림이 없습니다."));
        notificationRepository.delete(notification);
    }

    // * 마찬가지로 사이트 내 구독 리스트 각각에 대한 알림일 수정 필요 <= 구글 캘린더 API 필요
    @Override
    public void modifyNotification(Member member, Long notificationId, FcmSendDto req) {
//        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 알림이 없습니다."));
    }

}
