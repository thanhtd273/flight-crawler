package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class AirplaneDTO {
    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("production_line")
    private String productionLine;

    @JsonProperty("iata_type")
    private String IATAType;

    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("model_code")
    private String modelCode;

    @JsonProperty("icao_code_hex")
    private String ICAOCodeHex;

    @JsonProperty("iata_code_short")
    private String IATACodeShort;

    @JsonProperty("construction_number")
    private String constructionNumber;

    @JsonProperty("test_registration_number")
    private String testRegistrationNumber;

    @JsonProperty("rollout_date")
    private Date rolloutDate;

    @JsonProperty("first_flight_date")
    private Date firstFlightDate;

    @JsonProperty("delivery_date")
    private Date deliveryDate;

    @JsonProperty("registration_date")
    private String registrationDate;

    @JsonProperty("line_number")
    private String lineNumber;

    @JsonProperty("plane_series")
    private String planeSeries;

    @JsonProperty("airline_iata_code")
    private String airlineIATACode;

    @JsonProperty("airline_icao_code")
    private String airlineICAOCode;

    @JsonProperty("plane_owner")
    private String planeOwner;

    @JsonProperty("engines_count")
    private String enginesCount;

    @JsonProperty("engines_type")
    private String enginesType;

    @JsonProperty("plane_age")
    private String planeAge;

    @JsonProperty("plane_status")
    private String planeStatus;

    @JsonProperty("plane_class")
    private String planeClass;
}
