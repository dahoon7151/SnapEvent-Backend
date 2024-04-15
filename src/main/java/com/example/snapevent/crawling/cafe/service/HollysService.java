package com.example.snapevent.crawling.cafe.service;

import com.example.snapevent.crawling.SeleniumHelper;
import com.example.snapevent.crawling.cafe.dto.HollysDto;
import com.example.snapevent.crawling.cafe.entity.Hollys;
import com.example.snapevent.crawling.cafe.repository.HollysRepository;
import com.example.snapevent.crawling.embedded.DateRange;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Scheduler")
public class HollysService {

    private final HollysRepository hollysRepository;
    private final SeleniumHelper seleniumHelper;

    @Scheduled(cron = "0 0 2 * * *")
    public List<HollysDto> getHollysDatas() {
        List<HollysDto> hollysDtoList = new ArrayList<>();

        WebDriver driver = seleniumHelper.initDriver();
        driver.get("https://www.hollys.co.kr/news/event/list.do?pageNo=1&closeYn=&division=");

        List<WebElement> elements = driver.findElements(By.cssSelector("div.event_list_wr .event_listBox"));

        for (WebElement element : elements) {
            // a.event_finish를 만나면 반복문 종료
            if (!element.findElements(By.cssSelector("a.event_finish")).isEmpty()) {
                break;
            }

            String title = getTitle(element);
            String image = element.findElement(By.cssSelector("a img")).getAttribute("src");
            DateRange dateRange = getDateRange(element);
            String href = getHref(element);

            Hollys hollys = Hollys.builder()
                    .title(title)
                    .dateRange(dateRange)
                    .image(image)
                    .href(href)
                    .build();
            hollysRepository.save(hollys);
            hollysDtoList.add(new HollysDto(hollys));

        }
        driver.quit();
        return hollysDtoList;
    }

    private String getTitle(WebElement element) {
        // (title)sort 클래스 제거
        String parentElement = element.findElement(By.cssSelector("span")).getText();

        if (parentElement.startsWith("매장이벤트")) {
            return parentElement.replace("매장이벤트", "").trim();
        } else if (parentElement.startsWith("멤버십 이벤트")) {
            return parentElement.replace("멤버십 이벤트", "").trim();
        } else if (parentElement.startsWith("온라인 이벤트")) {
            return parentElement.replace("온라인 이벤트", "").trim();
        } else {
            return parentElement.replace("판촉물이벤트", "").trim();
        }
    }

    private String getHref(WebElement element) {
        // 하이퍼링크 추출
        WebElement linkElement = element.findElement(By.cssSelector("a[onclick*='onDetail']"));
        String onclickAttributeValue = linkElement.getAttribute("onclick");
        String[] splitParts = onclickAttributeValue.split(";");
        String extractedValue = splitParts[0].trim(); // extractedValue = javascript:onDetail(414)
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(extractedValue);
        String extractedNumber = "";

        if (matcher.find()) {
            extractedNumber = matcher.group(1);
        }

        try {

            String baseUrl = "https://www.hollys.co.kr/news/event/view.do?idx=";
            return baseUrl.concat(extractedNumber).concat("&pageNo=1&division=");
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Not found extractedNumber");
        }
    }

    private DateRange getDateRange(WebElement element) {
        // (date)span 태그 제거
        WebElement dateElement = element.findElement(By.cssSelector(".event_date"));
        String dateText = dateElement.getText().replace("공지 기간", "").trim();

        LocalDate startDate = null;
        LocalDate endDate = null;

        // '-'를 기준으로 시작 날짜, 종료 날짜를 나눔
        String[] dateParts = dateText.split("~");

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }

    /*private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }*/
}