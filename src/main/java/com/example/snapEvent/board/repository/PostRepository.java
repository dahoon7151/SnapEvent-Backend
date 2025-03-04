package com.example.snapEvent.board.repository;

import com.example.snapEvent.board.dto.PostResponseDto;
import com.example.snapEvent.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);
}
