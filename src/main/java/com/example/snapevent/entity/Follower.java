package com.example.snapevent.entity;

import com.example.snapevent.entity.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follower extends BaseTimeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private String followerId;
    @Column
    private String followerNickname;
    /*@Type(JsonType.class)
    @Column
    private Map<String, String> subList;*/
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member memberId;
}
