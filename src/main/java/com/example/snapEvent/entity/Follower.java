package com.example.snapEvent.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Follower {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private String followerId;
    @Column
    private String followerNickname;
    @Type(JsonType.class)
    @Column
    private Map<String, String> subList;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member memberId;
}
