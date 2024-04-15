package com.example.snapevent.crawling.dto;

import com.example.snapevent.crawling.entity.InterPark;
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

    public InterParkDto(InterPark interPark) {
        this.title = interPark.getTitle();
        this.ticketOpenDate = String.valueOf(interPark.getTicketOpenDate());
        this.href = interPark.getHref();
        this.image = interPark.getImage();
    }
}
