package com.example.snapevent.crawling.service;

import com.example.snapevent.crawling.dto.SsfDto;
import com.example.snapevent.crawling.embedded.DateRange;
import com.example.snapevent.crawling.entity.Ssf;
import com.example.snapevent.crawling.repository.SsfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Scheduler")
public class SsfService {

    private final SsfRepository ssfRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public List<SsfDto> getSsfDatas() throws IOException {
        List<SsfDto> ssfDtoList = new ArrayList<>();

        String url = "https://www.ssfshop.com/event/list";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("div.list-area ul.ssf-events li");
        List<Element> limitedContents = contents.subList(0, 10);

        for (Element content : limitedContents) {

            DateRange dateRange = getDateRange(content);

            Ssf ssf = Ssf.builder()
                    .title(content.select(".tit").text())
                    .description(content.select(".desc").text())
                    .dateRange(dateRange)
                    .image(resolveImageSrc(content.select("a img")))
                    .href(content.select("a").attr("abs:href"))
                    .build();
            ssfRepository.save(ssf);
            ssfDtoList.add(new SsfDto(ssf));
        }
        return ssfDtoList;
    }

    private DateRange getDateRange(Element content) {
        String dateText = content.select(".date").text();
        LocalDate startDate = null;
        LocalDate endDate = null;

        // '-'를 기준으로 시작 날짜, 종료 날짜를 나눔
        String[] dateParts = dateText.split("-");

        if (dateParts.length == 2) {

            String startDateStr = dateParts[0].trim();
            startDate = parseDate(startDateStr);

            // Extract end date
            String endDateStr = dateParts[1].trim();
            endDate = parseDate(endDateStr);
        } else {
            // Handle invalid date format
            log.error("Invalid date format={}", dateText);
        }

        return new DateRange(startDate, endDate);

    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d");
        return LocalDate.parse(dateStr, formatter);
    }

    // img attr이 달라서 따로 메서드로 해결
    private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }
}
