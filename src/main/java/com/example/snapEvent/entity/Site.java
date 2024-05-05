package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.subscribe.entity.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Site extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SITE_ID")
    private Long id;

    private String siteName;
    private String url;
    private String crawlingRules; // Jsoup에 따라 필요없을 수도 있겠네요

    @OneToMany(mappedBy = "site")
    @Builder.Default // OneToMany 관계는 빌더 패턴을 사용할 때 필수입니다
    private List<Event> events = new ArrayList<>();

//    @OneToMany(mappedBy = "site")
//    @Builder.Default
//    private List<Subscription> subscriptions = new ArrayList<>();
}
