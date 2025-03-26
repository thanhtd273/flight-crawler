package com.thanhtd.flight.crawler.controller;

import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.constant.ResponseMessage;
import com.thanhtd.flight.crawler.exception.ExceptionHandler;
import com.thanhtd.flight.crawler.model.Airline;
import com.thanhtd.flight.crawler.service.AirlineService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airlines")
@RequiredArgsConstructor
@Slf4j
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping(value = "/crawl-data")
    public APIResponse crawlAirlines(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airline> airlines = airlineService.crawlData();
            log.info("Succeeded crawling airline data from AviationSack, size: {}", airlines.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airlines);
        } catch (Exception e) {
            log.error("Failed to crawl airline data from Aviation Stack, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/all")
    public APIResponse getAllAirlines(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airline> airlines = airlineService.getAllAirlines();
            log.info("GET /api/v1/airlines/all, count: {}", airlines.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airlines);
        } catch (Exception e) {
            log.error("GET /api/v1/airlines/all error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/import")
    public APIResponse importAirlines(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airline> airlines = airlineService.importAirlineFromJsonFile();
            log.info("GET /api/v1/airlines/import, count: {}", airlines.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airlines);
        } catch (Exception e) {
            log.error("GET /api/v1/airlines/import error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
