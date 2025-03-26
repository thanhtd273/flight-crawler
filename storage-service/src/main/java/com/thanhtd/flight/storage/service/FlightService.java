package com.thanhtd.flight.storage.service;

import com.thanhtd.flight.storage.exception.LogicException;
import com.thanhtd.flight.storage.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();

    Flight findByFlightId(String flightId) throws LogicException;

    Flight createFlight(Flight flight);

    List<Flight> createMany(List<Flight> flights);
}
