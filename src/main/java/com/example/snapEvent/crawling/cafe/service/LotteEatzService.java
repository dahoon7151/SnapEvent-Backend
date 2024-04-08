package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.SeleniumHelper;
import com.example.snapEvent.crawling.cafe.dto.LotteEatzDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteEatzService {

    private final SeleniumHelper seleniumHelper;

    public List<LotteEatzDto> getLotteEatzDatas() {
        List<LotteEatzDto> lotteEatzDtoList = new ArrayList<>();

        // href가 동적으로 실행되서 셀레니움 빈 가져옴
        WebDriver driver = seleniumHelper.initDriver();
        driver.get("https://www.lotteeatz.com/event/main");

        // 이벤트 목록을 가져옴
        List<WebElement> elements = driver.findElements(By.cssSelector(".grid-list li"));

        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector(".grid-title")).getText();
            String date = element.findElement(By.cssSelector(".grid-period")).getText();
            String image = element.findElement(By.cssSelector(".thumb-box img")).getAttribute("src");
            String href = extractHref(element, driver);
            String field = element.findElement(By.cssSelector(".badge-wrap")).getText();

            LotteEatzDto lotteEatzDto = LotteEatzDto.builder()
                    .title(title)
                    .date(date)
                    .image(image)
                    .href(href)
                    .field(field)
                    .build();
            lotteEatzDtoList.add(lotteEatzDto);
        }
        driver.quit();
        return lotteEatzDtoList;
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
