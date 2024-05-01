package com.example.snapEvent.board.service;

import com.example.snapEvent.board.dto.LikeResponseDto;
import com.example.snapEvent.board.entity.Like;
import com.example.snapEvent.board.repository.LikeRepository;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.dto.PostResponseDto;
import com.example.snapEvent.board.repository.PostRepository;
import com.example.snapEvent.board.entity.Post;
import com.example.snapEvent.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Page<Post> sortPostlist(int page, int postCount, String order) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (order.equals("recent")) {
            sorts.add(Sort.Order.desc("createdDate"));
        } else if (order.equals("old")) {
            sorts.add(Sort.Order.asc("createdDate"));
        } else if (order.equals("comment")) {
                sorts.add(Sort.Order.desc("commentCount"));
        } else if (order.equals("like")) {
            sorts.add(Sort.Order.desc("likeCount"));
        } else {
            throw new IllegalArgumentException("잘못된 정렬 기준");
        }
        Pageable pageable = PageRequest.of(page, postCount, Sort.by(sorts));

        return postRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public PostResponseDto showPost(String username, Long id) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        if (post.getMember().getUsername().equals(member.getUsername())) {
            return new PostResponseDto(post, true);
        } else {
            return new PostResponseDto(post, false);
        }
    }

    @Transactional
    @Override
    public PostResponseDto writePost(String username, PostDto postDto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));
        Post post = postRepository.save(postDto.toEntity(member));
        log.info("글 작성 완료 {}", post.getId());

        return new PostResponseDto(post);
    }

    @Transactional
    @Override
    public LikeResponseDto like(String username, Long id) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        if (likeRepository.findByMemberAndPost(member, post).isPresent()) {
            Like like = likeRepository.findByMemberAndPost(member, post).orElse(null);
            likeRepository.delete(like);

            return new LikeResponseDto(post.countLike(false), false);
        } else {
            Like like = Like.builder()
                    .member(member)
                    .post(post)
                    .build();
            likeRepository.save(like);

            return new LikeResponseDto(post.countLike(true), true);
        }
    }

    @Transactional
    @Override
    public PostResponseDto modifyPost(String username, Long id, PostDto postDto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        // 해당 사용자가 게시글 작성자와 동일한지 검증(비정상적인 접근을 통해 수정 요청 시 대비)
        if (post.getMember().equals(member)) {
            Post modifiedPost = post.update(postDto);

            return new PostResponseDto(modifiedPost);
        } else {
            throw new IllegalArgumentException("사용자에게 수정 권한이 없습니다.");
        }
    }

    @Transactional
    @Override
    public void deletePost(String username, Long id) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID(PK)에 대한 글이 없습니다."));
        log.info("id : {}인 게시글 조회", post.getId());

        // 해당 사용자가 게시글 작성자와 동일한지 검증(비정상적인 접근을 통해 삭제 요청 시 대비)
        if (post.getMember().equals(member)) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("사용자에게 삭제 권한이 없습니다.");
        }
    }

    @Transactional
    @Override
    public PostResponseDto showNearPost(Long id) {
        String before = "이전 게시물이 없습니다.";
        String after = "다음 게시물이 없습니다.";

        Optional<Post> beforePost = postRepository.findById(id-1);
        if (beforePost.isPresent()) {
            before = beforePost.get().getTitle();
        }
        Optional<Post> afterPost = postRepository.findById(id+1);
        if (afterPost.isPresent()) {
            after = afterPost.get().getTitle();
        }

        return new PostResponseDto(before, after);
    }
}
