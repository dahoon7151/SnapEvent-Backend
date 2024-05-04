package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.CommentDto;
import com.example.snapEvent.board.dto.CommentResponseDto;
import com.example.snapEvent.board.dto.PostResponseDto;
import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.board.entity.Post;
import com.example.snapEvent.board.repository.CommentRepository;
import com.example.snapEvent.board.repository.PostRepository;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public List<CommentResponseDto> showCommentlist(String username, Long postId) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        List<Comment> commentList = post.getComments();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        boolean b = false;

        for (Comment comment : commentList) {
            if (member.equals(comment.getMember())) {b = true;}

            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .id(comment.getId())
                    .nickname(comment.getMember().getNickname())
                    .content(comment.getCommentContent())
                    .myComment(b)
                    .build();

            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }

    @Transactional
    @Override
    public CommentResponseDto writeComment(String username, Long postId, CommentDto commentDto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        Comment comment = commentRepository.save(commentDto.toEntity(member, post));

        return new CommentResponseDto(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto modifyComment(String username, Long commentId, CommentDto commentDto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 댓글이 없습니다."));
        log.info("id : {}인 댓글 조회", comment.getId());

        // 해당 사용자가 게시글 작성자와 동일한지 검증(비정상적인 접근을 통해 수정 요청 시 대비)
        if (comment.getMember().equals(member)) {
            Comment modifiedComment = comment.update(commentDto);

            return new CommentResponseDto(modifiedComment);
        } else {
            throw new IllegalArgumentException("사용자에게 수정 권한이 없습니다.");
        }
    }

    @Transactional
    @Override
    public void deleteComment(String username, Long commentId) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 댓글이 없습니다."));
        log.info("id : {}인 댓글 조회", comment.getId());

        // 해당 사용자가 게시글 작성자와 동일한지 검증(비정상적인 접근을 통해 삭제 요청 시 대비)
        if (comment.getMember().equals(member)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("사용자에게 삭제 권한이 없습니다.");
        }
    }

}
