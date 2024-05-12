package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.SeleniumHelper;
import com.example.snapEvent.crawling.cafe.dto.StarBucksDto;
import com.example.snapEvent.crawling.cafe.entity.StarBucks;
import com.example.snapEvent.crawling.cafe.repository.StarBucksRepository;
import com.example.snapEvent.crawling.embedded.DateRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarBucksService {

    private final StarBucksRepository starBucksRepository;
    private final SeleniumHelper seleniumHelper;

    public List<StarBucksDto> getStarBucksDatas() {
        List<StarBucksDto> starBucksDtoList = new ArrayList<>();

        WebDriver driver = seleniumHelper.initDriver();
        driver.get("https://www.starbucks.co.kr/whats_new/campaign_list.do");

        // 이벤트 목록을 가져옴
        List<WebElement> elements = driver.findElements(By.cssSelector("section .campaign_list ul li"));
        List<WebElement> limitedWebElements = elements.subList(0, 10);

        for (WebElement element : limitedWebElements) {
            // 종료된 이벤트가 표시되면 종료
            if (!element.findElements(By.cssSelector(".i_dimm")).isEmpty()) {
                // .i_end_event 클래스를 찾은 경우 반복문 종료
                break;
            }

            String title = element.findElement(By.cssSelector("h4")).getText();
            DateRange dateRange = getDateRange(element);
            String image = element.findElement(By.cssSelector("a img")).getAttribute("src");
            String href = extractHref(element);

            if (isTitleExist(title)) {
                log.debug("Skipping duplicate title: {}", title);
                StarBucks existingStarBucks = starBucksRepository.findByTitle(title);
                if (existingStarBucks != null) {
                    starBucksDtoList.add(new StarBucksDto(existingStarBucks));
                }
                continue;
            }

            StarBucks starBucks = StarBucks.builder()
                    .title(title)
                    .dateRange(dateRange)
                    .image(image)
                    .href(href)
                    .build();
            starBucksRepository.save(starBucks);
            starBucksDtoList.add(new StarBucksDto(starBucks));
        }
        driver.quit();
        return starBucksDtoList;
    }

    private boolean isTitleExist(String title) {
        return starBucksRepository.existsByTitle(title);
    }


    private DateRange getDateRange(WebElement element) {
        String dateElement = element.findElement(By.cssSelector(".date")).getText();

        LocalDate startDate = null;
        LocalDate endDate = null;

        // '-'를 기준으로 시작 날짜, 종료 날짜를 나눔
        String[] dateParts = dateElement.split("~");

        if (dateParts.length == 2) {

            String startDateStr = dateParts[0].trim();
            startDate = parseDate(startDateStr);

            // Extract end date
            String endDateStr = dateParts[1].trim();
            endDate = parseDate(endDateStr);

        } else if (dateParts.length == 1) {
            String startDateStr = dateParts[0].trim();
            startDate = parseOneDate(startDateStr);

            // starDate를 endDate로 복사
            endDate = startDate;

        } else {
            // Handle invalid date format
            log.error("Invalid date format={}", dateElement);
        }

        return new DateRange(startDate, endDate);
    }

    private LocalDate parseOneDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(dateStr, formatter);
    }

    private LocalDate parseDate(String dateStr) {
        // endDateStr이 "소진 시 까지"이면 2099-12-31로 설정
        if (dateStr.startsWith("소진")) {
            return LocalDate.of(2099, 12, 31);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        }
    }


    private String extractHref(WebElement element) {
        String prodValue = element.findElement(By.cssSelector("a")).getAttribute("prod");
        String baseUrl = "https://www.starbucks.co.kr/whats_new/campaign_view.do?pro_seq=";
        return baseUrl.concat(prodValue).concat("&menu_cd=");
    }
}

