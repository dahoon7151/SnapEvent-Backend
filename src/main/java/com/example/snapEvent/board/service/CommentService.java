package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.CommentDto;
import com.example.snapEvent.board.dto.CommentResponseDto;
import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.member.entity.Member;

import java.util.List;

public interface CommentService {
    // 댓글 삭제

    // 댓글 수정
    public List<CommentResponseDto> showCommentlist(Member member, Long postId);

    public CommentResponseDto writeComment(Member member, Long postId, CommentDto commentDto);

    public CommentResponseDto modifyComment(Member member, Long commentId, CommentDto commentDto);

    public void deleteComment(Member member, Long commentId);
}
