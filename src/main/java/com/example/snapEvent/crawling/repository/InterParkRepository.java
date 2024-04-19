package com.example.snapEvent.crawling.repository;

import com.example.snapEvent.crawling.entity.InterPark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterParkRepository extends JpaRepository<InterPark, Long> {
    boolean existsByTitle(String title);

    InterPark findByTitle(String title);
}
