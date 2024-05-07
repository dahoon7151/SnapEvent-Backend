package com.example.snapEvent.crawling.entity;

import com.example.snapEvent.crawling.embedded.DateRange;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class OliveYoung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Embedded
    @Column(nullable = false)
    private DateRange dateRange;
    private String image;
    private String href;



    @Builder
    public OliveYoung(String title, String description, DateRange dateRange, String image, String href) {
        this.title = title;
        this.description = description;
        this.dateRange = dateRange;
        this.image = image;
        this.href = href;
    }
}
