package com.thanhtd.flight.crawler.controller;

import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.constant.ResponseMessage;
import com.thanhtd.flight.crawler.exception.ExceptionHandler;
import com.thanhtd.flight.crawler.model.City;
import com.thanhtd.flight.crawler.service.CityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    @GetMapping("/all")
    public APIResponse getAllCities(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<City> cities = cityService.getAllCities();
            logger.info("GET /api/v1/cities/all, count: {}", cities.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, cities);
        } catch (Exception e) {
            logger.error("GET /api/v1/cities/all error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/{cityId}")
    public APIResponse findByCityId(@PathVariable("cityId") UUID cityId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.findByCityId(cityId);
            logger.info("GET /api/v1/cities/{}", cityId);
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("GET /api/v1/cities/{} error: {}", cityId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/crawl-data")
    public APIResponse crawlCities(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<City> cities = cityService.crawlData();
            logger.info("Succeeded crawling city data from AviationSack, size: {}", cities.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, cities);
        } catch (Exception e) {
            logger.error("Failed to crawl city data from Aviation Stack, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/import")
    public APIResponse importCities(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<City> cities = cityService.importCityFromJsonFile();
            logger.info("GET /api/v1/cities/import, count: {}", cities.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, cities);
        } catch (Exception e) {
            logger.error("GET /api/v1/cities/import error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
