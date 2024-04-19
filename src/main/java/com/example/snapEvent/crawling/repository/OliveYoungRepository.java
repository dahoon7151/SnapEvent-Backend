package com.example.snapEvent.crawling.repository;

import com.example.snapEvent.crawling.entity.OliveYoung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OliveYoungRepository extends JpaRepository<OliveYoung, Long> {
    boolean existsByTitle(String title);
    OliveYoung findByTitle(String title);
}
