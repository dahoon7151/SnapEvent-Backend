package com.example.snapevent.crawling.cafe.dto;

import com.example.snapevent.crawling.cafe.entity.StarBucks;
import com.example.snapevent.crawling.embedded.DateRange;
import lombok.*;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StarBucksDto {

    private String title;
    private DateRange dateRange;
    private String image;
    private String href;

    public StarBucksDto(StarBucks starBucks) {
        this.title = starBucks.getTitle();
        this.dateRange = starBucks.getDateRange();
        this.image = starBucks.getImage();
        this.href = starBucks.getHref();
    }
}
