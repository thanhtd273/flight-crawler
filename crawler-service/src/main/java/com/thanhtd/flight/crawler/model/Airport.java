package com.thanhtd.flight.crawler.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Data
@Table("airports")
public class Airport {
    @PrimaryKey
    @Column("airport_id")
    private UUID airportId;

    @Column("name")
    private String name;

    @Column("code")
    private String code;

    @Column("city_code")
    private String cityCode;

    @Column("country_name")
    private String countryName;

    @Column("created_at")
    private Date createdAt;

    @Column("updated_at")
    private Date updatedAt;

    @Column("status")
    private String status;
}
