package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CityDTO {

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("iata_code")
    private String IATACode;

    @JsonProperty("country_iso2")
    private String countryISO2;

    private String latitude;

    private String longitude;

    private String timezone;

    @JsonProperty("gmt")
    private String GMT;

    @JsonProperty("geoname_id")
    private String geoNameId;


}
