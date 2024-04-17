package com.example.snapevent.entity;

import com.example.snapevent.entity.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long id;

    private String title;
    private String description;
    private String linkToSite;

    @OneToOne
    @JoinColumn(name = "NOTE_ID", unique = true) // 이벤트가 알림을 소유
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    private Site site;

    // 연관관계 편의 메서드
    public void addNotification(Notification notification) {
        this.notification = notification;
        notification.setEvent(this);
    }

}
