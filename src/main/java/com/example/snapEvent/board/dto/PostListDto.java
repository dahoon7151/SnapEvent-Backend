package com.example.snapEvent.board.dto;

import com.example.snapEvent.common.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class  PostListDto {
    private Long id;
    private String title;
    private int like;
    private int comment;
    private LocalDateTime createdTime;

    public PostListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.like = post.getLike();
        this.comment = post.getComment();
        this.createdTime = post.getCreatedDate();
    }
}
