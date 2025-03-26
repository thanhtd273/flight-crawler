package com.thanhtd.flight.crawler.controller;

import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.constant.ResponseMessage;
import com.thanhtd.flight.crawler.exception.ExceptionHandler;
import com.thanhtd.flight.crawler.model.Airport;
import com.thanhtd.flight.crawler.service.AirportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
@Slf4j
public class AirportController {
    
    private final AirportService airportService;

    @GetMapping(value = "/crawl-data")
    public APIResponse crawlAirports(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airport> airports = airportService.crawlData();
            log.info("Succeeded crawling airport data from AviationSack, size: {}", airports.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airports);
        } catch (Exception e) {
            log.error("Failed to crawl airport data from Aviation Stack, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/all")
    public APIResponse getAllAirports(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airport> airports = airportService.getAllAirports();
            log.info("GET /api/v1/airports/all, count: {}", airports.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airports);
        } catch (Exception e) {
            log.error("GET /api/v1/airports/all error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/import")
    public APIResponse importAirports(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airport> airports = airportService.importAirportFromJsonFile("data/airports.json");
            log.info("GET /api/v1/airports/import, count: {}", airports.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, airports);
        } catch (Exception e) {
            log.error("GET /api/v1/airports/import error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
