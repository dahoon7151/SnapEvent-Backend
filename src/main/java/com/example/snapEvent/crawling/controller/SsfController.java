package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.SsfDto;
import com.example.snapEvent.crawling.service.SsfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crawl")
public class SsfController {

    private final SsfService ssfService;

    @GetMapping("/ssf-shop")
    public List<SsfDto> ssf() {
        try {
            return ssfService.getSsfDatas();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
