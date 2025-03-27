package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AirlineDTO {
    @JsonProperty("airline_name")
    private String airlineName;

    @JsonProperty("iata_code")
    private String IATACode;

    @JsonProperty("iata_prefix_accounting")
    private String IATAPrefixAccounting;

    @JsonProperty("icao_code")
    private String ICAOCode;

    private String callsign;

    private String type;

    private String status;

    @JsonProperty("fleet_size")
    private String fleetSize;

    @JsonProperty("fleet_average_age")
    private String fleetAverageAge;

    @JsonProperty("date_founded")
    private String dateFounded;

    @JsonProperty("hub_code")
    private String hubCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("country_iso2")
    private String countryISO2;
}
