package com.example.snapEvent.board.repository;

import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findByPost(Post post);

    Optional<Comment> findById(Long id);
}
