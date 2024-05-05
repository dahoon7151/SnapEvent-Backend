package com.example.snapEvent.crawling.cafe.entity;

import com.example.snapEvent.crawling.embedded.DateRange;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Ediya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Embedded
    @Column(nullable = false)
    private DateRange dateRange;
    private String image;
    private String href;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isSubscribed;

    @Builder
    public Ediya(String title, DateRange dateRange, String image, String href) {
        this.title = title;
        this.dateRange = dateRange;
        this.image = image;
        this.href = href;
    }
}
