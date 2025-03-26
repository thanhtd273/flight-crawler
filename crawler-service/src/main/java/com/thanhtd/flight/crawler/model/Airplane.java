package com.thanhtd.flight.crawler.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table("airplanes")
@Data
public class Airplane {
    @PrimaryKey
    @Column("airplane_id")
    private UUID airplaneId;

    @Column("name")
    private String name;

    @Column("code")
    private String code;

    @Column("owner")
    private String owner;

    @Column("created_at")
    private Date createdAt;

    @Column("updated_at")
    private Date updatedAt;

    @Column("status")
    private String status;
}
