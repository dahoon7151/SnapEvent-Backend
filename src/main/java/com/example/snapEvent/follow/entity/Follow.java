package com.example.snapEvent.follow.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"to_user", "from_user"})
)
@IdClass(Follow.PK.class) // to_user, from_user PK가 2개라 복합키 매핑
@Getter
public class Follow extends BaseTimeEntity {

    // from_user: 팔로우를 요청하는 사용자(팔로잉), to_user: 팔로우를 요청받은 사용자(팔로워)
    @Id
    @Column(name = "to_user", insertable = false, updatable = false)
    private Long toUser;

    @Id
    @Column(name = "from_user", insertable = false, updatable = false)
    private Long fromUser;

    public Follow(Long toUser, Long fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

    // Follow 엔티티의 복합 기본키
    public static class PK implements Serializable {
        Long toUser;
        Long fromUser;
    }
}
