package com.thanhtd.flight.crawler.controller;

import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.constant.ResponseMessage;
import com.thanhtd.flight.crawler.dto.FlightDTO;
import com.thanhtd.flight.crawler.exception.ExceptionHandler;
import com.thanhtd.flight.crawler.model.Flight;
import com.thanhtd.flight.crawler.service.FlightService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
@Slf4j
public class FlightController {

    private final FlightService flightService;

    @GetMapping(value = "/crawl-data")
    public APIResponse crawlFlights(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Flight> flights = flightService.crawlData();
            log.info("Succeeded crawling flight data from AviationSack, size: {}", flights.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, flights);
        } catch (Exception e) {
            log.error("Failed to crawl flight data from Aviation Stack, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/all")
    public APIResponse getAllFlights(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Flight> flights = flightService.getAllFlights();
            log.info("GET /api/v1/flights/all, count: {}", flights.size());
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, flights);
        } catch (Exception e) {
            log.error("GET /api/v1/flights/all error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/{flightId}")
    public APIResponse findByFlightId(@PathVariable("flightId") UUID flightId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Flight flight = flightService.flightByFlightId(flightId);
            log.info("GET /api/v1/flights/{} success: {}", flightId, flight);
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, flight);
        } catch (Exception e) {
            log.error("GET /api/v1/flights/{} error: {}", flightId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping()
    public APIResponse createFlight(@RequestBody FlightDTO flightDTO, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Flight flight = flightService.createFlight(flightDTO);
            log.info("Succeeded creating flight: {}", flight);
            return new APIResponse(ErrorCode.SUCCESS, ResponseMessage.SUCCESS, System.currentTimeMillis() - start, flight);
        } catch (Exception e) {
            log.error("Failed to create flight error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
