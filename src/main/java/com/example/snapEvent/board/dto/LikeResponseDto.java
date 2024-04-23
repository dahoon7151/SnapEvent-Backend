package com.example.snapEvent.board.dto;

import com.example.snapEvent.board.entity.Like;
import com.example.snapEvent.common.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {
    private int likeCount;
    private String message;

    public LikeResponseDto(Post post, boolean b) {
        this.likeCount = post.getLikeCount();
        if (b) {this.message = "좋아요 등록";}
        else {this.message = "좋아요 취소";}
    }
}
