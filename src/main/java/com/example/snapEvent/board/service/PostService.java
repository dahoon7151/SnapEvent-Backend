package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.LikeResponseDto;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.board.entity.Post;
import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.dto.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {

    public Page<Post> sortPostlist(int page, int postCount, String order);

    public PostResponseDto showPost(Member member, Long id);

    public PostResponseDto writePost(Member member, PostDto postDto);

    public LikeResponseDto like(Member member, Long id);

    public PostResponseDto modifyPost(Member member, Long id, PostDto postDto);

    public void deletePost(Member member, Long id);

    public PostResponseDto showNearPost(Long id);
}
