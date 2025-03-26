package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlightAirportDTO {
    private String airport;

    private String timezone;

    private String iata;

    private String icao;

    private String terminal;

    private String gate;

    private Integer delay;

    private Date scheduled;

    private Date estimated;

    private Date actual;

    @JsonProperty("estimated_runway")
    private Date estimatedRunway;

    @JsonProperty("actual_runway")
    private Date actualRunway;
}
