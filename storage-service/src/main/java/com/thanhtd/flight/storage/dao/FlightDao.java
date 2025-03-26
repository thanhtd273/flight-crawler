package com.thanhtd.flight.storage.dao;

import com.thanhtd.flight.storage.model.Flight;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDao extends ElasticsearchRepository<Flight, String> {
    Flight findByFlightId(String flightId);
}
