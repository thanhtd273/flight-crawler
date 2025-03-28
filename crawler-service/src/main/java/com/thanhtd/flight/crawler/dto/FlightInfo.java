package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FlightInfo {

    private String number;

    private String iata;

    private String icao;

    @JsonProperty("code_shared")
    private CodeSharedDTO codeShared;
}
