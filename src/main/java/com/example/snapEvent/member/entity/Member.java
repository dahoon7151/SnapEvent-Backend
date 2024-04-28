package com.example.snapEvent.member.entity;

import com.example.snapEvent.entity.Notification;
import com.example.snapEvent.entity.Subscription;
import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = "id")
@Table(name = "MEMBERS")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username; // 활동 ID(userId -> username)

    @Column(nullable = false)
    private String password;

    @Column
    private String nickname; // 사용자가 자유롭게 작명 가능

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public MemberDto from(Member member) {
        return MemberDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    public Member update(String encodedPassword, String nickname) {
        this.password = encodedPassword;
        this.nickname = nickname;
        return this;
    }
}