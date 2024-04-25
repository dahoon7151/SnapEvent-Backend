package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.LikeResponseDto;
import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.dto.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {
    // 게시글 작성, 수정, 삭제

    // 댓글 작성, 수정, 삭제

    // 좋아요, 좋아요 취소 (하나로)

    // 게시글들 리스트

    // 게시물 클릭 시 본인 게시물or 댓글 확인, 이전/다음 게시글 노출
    public Page<Post> sortPostlist(int page, int postCount, String order);

    public PostResponseDto showPost(Member member, Long id);

    public PostResponseDto writePost(Member member, PostDto postDto);

    public LikeResponseDto like(Member member, Long id);

    public PostResponseDto modifyPost(Member member, Long id, PostDto postDto);

    public void deletePost(Member member, Long id);
}
