package com.example.snapEvent.crawling.cafe.controller;

import com.example.snapEvent.crawling.cafe.dto.EdiyaDto;
import com.example.snapEvent.crawling.cafe.dto.HollysDto;
import com.example.snapEvent.crawling.cafe.dto.LotteEatzDto;
import com.example.snapEvent.crawling.cafe.dto.StarBucksDto;
import com.example.snapEvent.crawling.cafe.service.EdiyaService;
import com.example.snapEvent.crawling.cafe.service.HollysService;
import com.example.snapEvent.crawling.cafe.service.LotteEatzService;
import com.example.snapEvent.crawling.cafe.service.StarBucksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crawl")
public class CafeController {

    private final HollysService hollysService;
    private final EdiyaService ediyaService;
    private final LotteEatzService lotteEatzService;
    private final StarBucksService starBucksService;

    //! 크롤링할 웹사이트가 점검중이라 데이터를 못불러오면 503 에러 터뜨림
    @GetMapping("/hollys-coffee")
    public List<HollysDto> hollys() {
        try {
            return hollysService.getHollysDatas();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/ediya-coffee")
    public List<EdiyaDto> ediya() {
        try {
            return ediyaService.getEdiyaDatas();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/lotte-eatz")
    public List<LotteEatzDto> eatz() {
        try {
            return lotteEatzService.getLotteEatzDatas();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/starbucks")
    public List<StarBucksDto> starbucks() {
        try {
            return starBucksService.getStarBucksDatas();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
