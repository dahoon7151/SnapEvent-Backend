package com.example.snapevent.crawling;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class SeleniumHelper {

    public WebDriver initDriver() {

        // 크롬 드라이버 설정
        // 1번째 코드는 로컬환경 경로
        /*System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");*/

        // 2번째 코드는 ec2 우분투 서버의 경로
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver-linux64/chromedriver");

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // WebSocket connection 에러 해결
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        options.addArguments("accept-lang=ko-KR,ko"); // 한글이 있으면 한글로 반환
        options.addArguments("headless");

        return new ChromeDriver(options);
    }
}
