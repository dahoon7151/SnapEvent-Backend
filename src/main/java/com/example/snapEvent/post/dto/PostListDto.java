package com.example.snapEvent.post.dto;

import com.example.snapEvent.common.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListDto {
    private Long id;
    private String title;
    private int like;
    private int comment;

    public PostListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.like = post.getLike();
        this.comment = post.getComment();
    }
}
