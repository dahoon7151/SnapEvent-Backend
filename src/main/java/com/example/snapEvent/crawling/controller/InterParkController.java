package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.InterParkDto;
import com.example.snapEvent.crawling.service.InterParkService;
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
public class InterParkController {

    private final InterParkService interParkService;

    @GetMapping("/interpark")
    public List<InterParkDto> interpark() {
        try {
            return interParkService.getInterParkDatas();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
