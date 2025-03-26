package com.thanhtd.flight.crawler.service;

import com.thanhtd.flight.crawler.dto.AirlineDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Airline;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AirlineService {
    List<Airline> getAllAirlines();

    Airline findByAirlineId(UUID airlineId) throws LogicException;

    Airline createAirline(AirlineDTO airlineDTO) throws LogicException;

    List<Airline> crawlData() throws LogicException;

    List<Airline> importAirlineFromJsonFile() throws IOException, LogicException;
}
