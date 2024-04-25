package com.example.snapEvent.common.entity;

import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.entity.Like;
import com.example.snapEvent.common.entity.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int commentCount;

//    @CreationTimestamp
//    @Column
//    private Timestamp postTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @Builder.Default
    private List<Like> likes = new ArrayList<>();

    public Post countLike(boolean b) {
        if (b) {this.likeCount += 1;}
        else {this.likeCount -= 1;}

        return this;
    }

    public Post update(PostDto postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();

        return this;
    }
}
