package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.SeleniumHelper;
import com.example.snapEvent.crawling.cafe.dto.LotteEatzDto;
import com.example.snapEvent.crawling.cafe.entity.LotteEatz;
import com.example.snapEvent.crawling.cafe.repository.LotteEatzRepository;
import com.example.snapEvent.crawling.embedded.DateRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Scheduler")
public class LotteEatzService {

    private final LotteEatzRepository lotteEatzRepository;
    private final SeleniumHelper seleniumHelper;

    @Scheduled(cron = "0 0 2 * * *")
    public List<LotteEatzDto> getLotteEatzDatas() {
        List<LotteEatzDto> lotteEatzDtoList = new ArrayList<>();

        // href가 동적으로 실행되서 셀레니움 빈 가져옴
        WebDriver driver = seleniumHelper.initDriver();
        driver.get("https://www.lotteeatz.com/event/main");

        // 이벤트 목록을 가져옴
        List<WebElement> elements = driver.findElements(By.cssSelector(".grid-list li"));
        List<WebElement> limitedWebElements = elements.subList(0, 10);

        for (WebElement element : limitedWebElements) {
            String title = element.findElement(By.cssSelector(".grid-title")).getText();
            DateRange dateRange = getDateRange(element);
            String image = element.findElement(By.cssSelector(".thumb-box img")).getAttribute("src");
            String href = extractHref(element, driver);
            String field = element.findElement(By.cssSelector(".badge-wrap")).getText();

            LotteEatz lotteEatz = LotteEatz.builder()
                    .title(title)
                    .dateRange(dateRange)
                    .image(image)
                    .href(href)
                    .field(field)
                    .build();
            lotteEatzRepository.save(lotteEatz);
            lotteEatzDtoList.add(new LotteEatzDto(lotteEatz));
        }
        driver.quit();
        return lotteEatzDtoList;
    }

    private DateRange getDateRange(WebElement element) {
        String dateElement = element.findElement(By.cssSelector(".grid-period")).getText();

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
        } else {
            // Handle invalid date format
            log.error("Invalid date format={}", dateElement);
        }

        return new DateRange(startDate, endDate);
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(dateStr, formatter);
    }

    private String extractHref(WebElement element, WebDriver driver) {
        WebElement linkElement = element.findElement(By.cssSelector("a.btn-link"));

        // 요소 위로 마우스 커서를 이동하여 JavaScript 이벤트를 트리거
        Actions action = new Actions(driver);
        action.moveToElement(linkElement).perform();

        String findHref = linkElement.getAttribute("data-href");
        String baseUrl = "https://www.lotteeatz.com";
        return baseUrl.concat(findHref);
    }

}
