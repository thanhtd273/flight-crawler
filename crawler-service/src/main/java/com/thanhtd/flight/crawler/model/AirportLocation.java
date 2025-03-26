package com.thanhtd.flight.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Date;

@Data
@UserDefinedType("airport_location")
@AllArgsConstructor
@NoArgsConstructor
public class AirportLocation {
    @Column("airport_name")
    private String airportName;

    @Column("airport_code")
    private String airportCode;

    @Column("city")
    private String city;

    @Column("country")
    private String country;

    @Column("scheduled")
    private Date scheduled;
}
