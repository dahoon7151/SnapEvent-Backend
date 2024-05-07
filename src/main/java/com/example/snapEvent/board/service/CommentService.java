package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.CommentDto;
import com.example.snapEvent.board.dto.CommentResponseDto;
import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.member.entity.Member;

import java.util.List;

public interface CommentService {
    // 댓글 삭제

    // 댓글 수정
    public List<CommentResponseDto> showCommentlist(String username, Long postId);

    public CommentResponseDto writeComment(String username, Long postId, CommentDto commentDto);

    public CommentResponseDto modifyComment(String username, Long commentId, CommentDto commentDto);

    public boolean deleteComment(String username, Long commentId);
}
