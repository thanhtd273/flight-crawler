package com.thanhtd.flight.crawler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dto.CrawlResult;
import com.thanhtd.flight.crawler.dto.FlightDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {

    Flight flightByFlightId(UUID flightId) throws LogicException;

    List<Flight> getAllFlights();

    Flight createFlight(FlightDTO flightDTO) throws LogicException;

    CrawlResult crawlData() throws LogicException, JsonProcessingException;

    ErrorCode deleteFlight(String flightId) throws LogicException;
}
