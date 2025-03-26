package com.thanhtd.flight.crawler.service;

import com.thanhtd.flight.crawler.dto.AirportDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Airport;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AirportService {

    List<Airport> getAllAirports();

    Airport findByAirportId(UUID airportId) throws LogicException;

    Airport findByName(String name);

    Airport createAirport(AirportDTO airportDTO) throws LogicException;

    List<Airport> crawlData() throws LogicException;

    List<Airport> importAirportFromJsonFile(String path) throws IOException, LogicException;
}
