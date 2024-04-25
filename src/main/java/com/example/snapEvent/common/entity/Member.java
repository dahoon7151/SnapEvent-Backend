package com.example.snapEvent.common.entity;

import com.example.snapEvent.board.entity.Like;
import com.example.snapEvent.common.entity.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = "id")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String nickname;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @Builder.Default
    private List<Like> likes = new ArrayList<>();

    public Member update(String encodedPassword, String nickname) {
        this.password = encodedPassword;
        this.nickname = nickname;
        return this;
    }
}