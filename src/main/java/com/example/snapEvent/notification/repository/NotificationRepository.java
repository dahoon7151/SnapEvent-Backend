package com.example.snapEvent.notification.repository;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByMember(Member member);
}
