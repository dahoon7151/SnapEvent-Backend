package com.example.snapEvent.notification.service;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.notification.dto.FcmSendDto;

public interface NotificationService {

    void saveNotification(String token, Member member);

    void deleteNotification(Member member, Long notificationId);

    void modifyNotification(Member member, Long notificationId, FcmSendDto req);
}
