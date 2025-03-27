package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirportDTO {
    @JsonProperty("airport_name")
    private String airportName;

    @JsonProperty("iata_code")
    private String IATACode;

    @JsonProperty("icao_code")
    private String ICAOCode;

    private String latitude;

    private String longitude;

    @JsonProperty("geoname_id")
    private String geoNameId;

    private String timezone;

    @JsonProperty("gmt")
    private String GMT;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("country_iso2")
    private String countryISO2;

    @JsonProperty("city_iata_code")
    private String cityIATACode;
}
