package com.thanhtd.flight.crawler.dao;

import com.thanhtd.flight.crawler.model.Airport;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirportDao extends CassandraRepository<Airport, UUID> {
    Airport findByAirportId(UUID airportId);

    Airport findByName(String name);
}
