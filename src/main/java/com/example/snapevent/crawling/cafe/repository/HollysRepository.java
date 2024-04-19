package com.example.snapevent.crawling.cafe.repository;

import com.example.snapevent.crawling.cafe.entity.Hollys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HollysRepository extends JpaRepository<Hollys, Long> {

    boolean existsByTitle(String title);
    Hollys findByTitle(String title);
}
