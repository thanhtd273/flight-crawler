package com.thanhtd.flight.storage.model;

import lombok.Data;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
public class AirportLocation {
//    @Field(type = FieldType.Keyword, name = "airport_name")
    private String airportName;

//    @Field(type = FieldType.Keyword, name = "airport_code")
    private String airportCode;

//    @Field(type = FieldType.Keyword, name = "city")
    private String city;

//    @Field(type = FieldType.Keyword, name = "country")
    private String country;

//    @Field(type = FieldType.Date, name = "scheduled")
    private Date scheduled;
}
