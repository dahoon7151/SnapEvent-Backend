package com.example.snapEvent.board.repository;

import com.example.snapEvent.board.entity.Like;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);
}
