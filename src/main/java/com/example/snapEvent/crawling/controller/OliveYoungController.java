package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.OliveYoungDto;
import com.example.snapEvent.crawling.service.OliveYoungService;
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
public class OliveYoungController {

    private final OliveYoungService oliveYoungService;

    @GetMapping("/olive-young")
    public List<OliveYoungDto> oliveYoung() {
        try {
            return oliveYoungService.getOliveYoungDatas();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
