package com.example.snapEvent.board.dto;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdTime;
    private String writerName;
    private boolean myPost;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdTime = post.getCreatedDate();
        this.writerName = post.getMember().getNickname();
    }

    public PostResponseDto(Post post, boolean b) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdTime = post.getCreatedDate();
        this.writerName = post.getMember().getNickname();
        this.myPost = b;
    }
}
