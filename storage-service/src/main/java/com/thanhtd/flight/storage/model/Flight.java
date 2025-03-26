package com.thanhtd.flight.storage.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "flights")
@Data
public class Flight {
    @Id
    @Field(type = FieldType.Keyword, name = "flight_id")
    private String flightId;

    @Field(type = FieldType.Keyword, name = "flight_code")
    private String flightCode;

    @Field(type = FieldType.Nested, name = "departure")
    private AirportLocation departure;

    @Field(type = FieldType.Nested, name = "arrival")
    private AirportLocation arrival;

    @Field(type = FieldType.Integer, name = "duration")
    private Integer duration;

    @Field(type = FieldType.Integer, name = "price")
    private Integer price;

    @Field(type = FieldType.Keyword, name = "currency")
    private String currency;

    @Field(type = FieldType.Keyword, name = "airline")
    private String airline;

    @Field(type = FieldType.Integer, name = "seats_available")
    private Integer seatsAvailable;

    @Field(type = FieldType.Keyword, name = "airplane_type")
    private String airplaneType;

    @Field(type = FieldType.Date, name = "flight_date")
    private Date flightDate;

    @Field(type = FieldType.Date, name = "created_at")
    private Date createdAt;

    @Field(type = FieldType.Date, name = "updated_at")
    private Date updatedAt;

    @Field(type = FieldType.Keyword, name = "status")
    private String status;
}
