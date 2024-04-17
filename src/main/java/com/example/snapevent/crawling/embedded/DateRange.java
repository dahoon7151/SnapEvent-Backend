package com.example.snapevent.crawling.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;
}
