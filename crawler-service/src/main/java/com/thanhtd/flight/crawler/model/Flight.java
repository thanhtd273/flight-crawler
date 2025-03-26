package com.thanhtd.flight.crawler.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table("flights")
@Data
public class Flight {
    @PrimaryKey
    @Column("flight_id")
    private UUID flightId;

    @Column("flight_code")
    private String flightCode;

    @Column("departure")
    private AirportLocation departure;

    @Column("arrival")
    private AirportLocation arrival;

    @Column("duration")
    private Integer duration;

    @Column("price")
    private Integer price;

    @Column("currency")
    private String currency;

    @Column("airline")
    private String airline;

    @Column("seats_available")
    private Integer seatsAvailable;

    @Column("airplane_type")
    private String airplaneType;

    @Column("flight_date")
    private String flightDate;

    @Column("created_at")
    private Date createdAt;

    @Column("updated_at")
    private Date updatedAt;

    @Column("status")
    private String status;
}
