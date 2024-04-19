package com.example.snapEvent.crawling.repository;

import com.example.snapEvent.crawling.entity.Ssf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SsfRepository extends JpaRepository<Ssf, Long> {

    boolean existsByTitle(String title);
    Ssf findByTitle(String title);
}
