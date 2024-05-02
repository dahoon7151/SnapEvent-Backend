package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.subscribe.entity.Subscription;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long id;

    private String linkToSite;
    private String alarmContent;
    private Boolean isEnabled; // 알람 플래그

    @OneToOne(mappedBy = "notification")
    @Setter(AccessLevel.PACKAGE)
    private Event event;

    @JoinColumn(name = "SUB_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void addMember(Member member) {
        this.member = member;
        member.getNotifications().add(this);
    }

    public void removeMember(Member member) {
        this.member = null;
        member.getNotifications().remove(this);
    }

}
