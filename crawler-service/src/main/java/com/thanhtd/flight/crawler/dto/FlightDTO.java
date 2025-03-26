package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    @JsonProperty("flight_date")
    private String flightDate;

    @JsonProperty("flight_status")
    private String flightStatus;

    private FlightAirportDTO departure;

    private FlightAirportDTO arrival;

    private FlightAirlineDTO airline;

    private FlightInfo flight;

    private AircraftDTO aircraft;

    private LiveDTO live;
}
