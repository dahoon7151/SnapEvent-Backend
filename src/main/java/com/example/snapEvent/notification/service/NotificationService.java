package com.example.snapEvent.notification.service;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.notification.dto.NotificationRequestDto;

import java.util.concurrent.ExecutionException;

public interface NotificationService {

    void saveNotification(String token, Member member);

    String getNotificationToken();

    void sendNotification(NotificationRequestDto req) throws ExecutionException, InterruptedException;

    void deleteNotification(Member member, Long notificationId);

    void modifyNotification(Member member, Long notificationId, NotificationRequestDto req);
}
