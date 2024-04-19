package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.cafe.dto.EdiyaDto;
import com.example.snapEvent.crawling.cafe.entity.Ediya;
import com.example.snapEvent.crawling.cafe.repository.EdiyaRepository;
import com.example.snapEvent.crawling.embedded.DateRange;
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
@Slf4j
@RequiredArgsConstructor
public class EdiyaService {

    private final EdiyaRepository ediyaRepository;

    // 매일 오전 2시에 업데이트
    @Scheduled(cron = "0 0 2 * * *")
    public List<EdiyaDto> getEdiyaDatas() throws IOException {

        List<EdiyaDto> ediyaDtoList = new ArrayList<>();

        String url = "https://ediya.com/contents/event.html?tb_name=event";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select(".board_e li");
        List<Element> limitedContents = contents.subList(0, 10);

        for (Element content : limitedContents) {

            // 종료 이벤트를 만나면 break
            if (!content.select(".end").isEmpty()) {
                break;
            }

            DateRange dateRange = getDateRange(content);

            String title = content.select(".board_e_con a").text();
            String image = resolveImageSrc(content.select("a img"));
            String href = content.select("a").attr("abs:href");

            if (isTitleExist(title)) {
                log.debug("Skipping duplicate title: {}", title);
                Ediya existingEdiya = ediyaRepository.findByTitle(title);
                if (existingEdiya != null) {
                    ediyaDtoList.add(new EdiyaDto(existingEdiya));
                }
                continue;
            }

            Ediya ediya = Ediya.builder()
                    .title(title)
                    .dateRange(dateRange)
                    .image(image)
                    .href(href)
                    .build();
            ediyaRepository.save(ediya);
            ediyaDtoList.add(new EdiyaDto(ediya));
        }
        return ediyaDtoList;
    }

    private boolean isTitleExist(String title) {
        return ediyaRepository.existsByTitle(title);
    }

    private DateRange getDateRange(Element content) {
        // (date)span 태그 제거
        Element eventDateElements = content.select("dd").first();
        assert eventDateElements != null;
        String dateExcludingSpan = eventDateElements.ownText();

        LocalDate startDate = null;
        LocalDate endDate = null;

        // '-'를 기준으로 시작 날짜, 종료 날짜를 나눔
        String[] dateParts = dateExcludingSpan.split("~");

        if (dateParts.length == 2) {

            String startDateStr = dateParts[0].trim();
            startDate = parseDate(startDateStr);

            // Extract end date
            String endDateStr = dateParts[1].trim();
            endDate = parseDate(endDateStr);
        } else {
            // Handle invalid date format
            log.error("Invalid date format={}", dateExcludingSpan);
        }

        return new DateRange(startDate, endDate);
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return LocalDate.parse(dateStr, formatter);
    }



    private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }

}
