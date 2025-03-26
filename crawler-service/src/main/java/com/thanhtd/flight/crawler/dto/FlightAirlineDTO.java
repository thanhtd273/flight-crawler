package com.thanhtd.flight.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlightAirlineDTO {
    private String name;

    private String iata;

    private String icao;
}
