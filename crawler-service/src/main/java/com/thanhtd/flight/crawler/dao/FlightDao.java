package com.thanhtd.flight.crawler.dao;

import com.thanhtd.flight.crawler.model.Flight;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightDao extends CassandraRepository<Flight, UUID> {
    Flight findByFlightId(UUID flightId);
}
