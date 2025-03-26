package com.thanhtd.flight.storage.controller;

import com.thanhtd.flight.storage.base.APIResponse;
import com.thanhtd.flight.storage.constant.ErrorCode;
import com.thanhtd.flight.storage.exception.ExceptionHandler;
import com.thanhtd.flight.storage.model.Flight;
import com.thanhtd.flight.storage.service.FlightService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@AllArgsConstructor
@Slf4j
public class FlightController {
    private final FlightService flightService;

    @GetMapping()
    public APIResponse getAllFlights(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Flight> flights = flightService.getAllFlights();
            log.info("Successfully get all flights, total: {}", flights.size());
            return new APIResponse(ErrorCode.SUCCESS, "success", System.currentTimeMillis() - start, flights);
        } catch (Exception e) {
            log.error("Failed to get all flights, error; {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
