package com.thanhtd.flight.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AircraftDTO {
    private String registration;

    private String iata;

    private String icao;

    private String icao24;
}
