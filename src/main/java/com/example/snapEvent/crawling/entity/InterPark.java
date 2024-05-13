package com.example.snapEvent.crawling.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class InterPark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(nullable = false)
    private String ticketOpenDate;
    private String href;
    private String image;



    @Builder
    public InterPark(String title, String ticketOpenDate, String href, String image) {
        this.title = title;
        this.ticketOpenDate = ticketOpenDate;
        this.href = href;
        this.image = image;
    }
}
