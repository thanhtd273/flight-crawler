package com.thanhtd.flight.crawler.dao;

import com.thanhtd.flight.crawler.model.Airline;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirlineDao extends CassandraRepository<Airline, UUID> {
    Airline findByAirlineId(UUID airlineId);
}
