package com.example.snapEvent.crawling.dto;

import com.example.snapEvent.crawling.embedded.DateRange;
import com.example.snapEvent.crawling.entity.Ssf;
import lombok.*;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SsfDto {
    private String title;
    private String description;
    private DateRange dateRange;
    private String image;
    private String href;

    public SsfDto(Ssf ssf) {
        this.title = ssf.getTitle();
        this.description = ssf.getDescription();
        this.dateRange = ssf.getDateRange();
        this.image = ssf.getImage();
        this.href = ssf.getHref();
    }
}
