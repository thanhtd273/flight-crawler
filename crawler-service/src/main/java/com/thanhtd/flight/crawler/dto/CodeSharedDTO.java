package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CodeSharedDTO {
    @JsonProperty("airline_number")
    private Integer airlineNumber;

    @JsonProperty("airline_iata")
    private String airlineIATA;

    @JsonProperty("airline_icao")
    private String airlineICAO;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("flight_iata")
    private String flightIATA;

    @JsonProperty("flight_icao")
    private String flightICAO;
}
