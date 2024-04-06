package com.example.snapEvent.crawling.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InterParkDto {

    private String title;
    private String ticketOpenDate;
    private String href;
    private String image;
}
