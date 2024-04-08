package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.SeleniumHelper;
import com.example.snapEvent.crawling.cafe.dto.StarBucksDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarBucksService {

    private final SeleniumHelper seleniumHelper;

    public List<StarBucksDto> getStarBucksDatas() {
        List<StarBucksDto> starBucksDtoList = new ArrayList<>();

        WebDriver driver = seleniumHelper.initDriver();
        driver.get("https://www.starbucks.co.kr/whats_new/campaign_list.do");

        // 이벤트 목록을 가져옴
        List<WebElement> elements = driver.findElements(By.cssSelector("section .campaign_list ul li"));

        for (WebElement element : elements) {
            // 종료된 이벤트가 표시되면 종료
            if (!element.findElements(By.cssSelector(".i_dimm")).isEmpty()) {
                // .i_end_event 클래스를 찾은 경우 반복문 종료
                break;
            }

            String title = element.findElement(By.cssSelector("h4")).getText();
            String date = element.findElement(By.cssSelector(".date")).getText();
            String image = element.findElement(By.cssSelector("a img")).getAttribute("src");
            String href = extractHref(element);

            StarBucksDto starBucksDto = StarBucksDto.builder()
                    .title(title)
                    .date(date)
                    .image(image)
                    .href(href)
                    .build();
            starBucksDtoList.add(starBucksDto);
            log.debug("starBucksDto={}", starBucksDto);
        }
        driver.quit();
        return starBucksDtoList;
    }

        private String extractHref(WebElement element){
            String prodValue = element.findElement(By.cssSelector("a")).getAttribute("prod");
            String baseUrl = "https://www.starbucks.co.kr/whats_new/campaign_view.do?pro_seq=";
            return baseUrl.concat(prodValue).concat("&menu_cd=");
        }

        public static void main (String[]args){
            StarBucksService starBucksService = new StarBucksService(new SeleniumHelper());
            starBucksService.getStarBucksDatas();

        }
    }

