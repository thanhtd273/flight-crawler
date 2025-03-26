package com.thanhtd.flight.storage.service.impl;

import com.thanhtd.flight.storage.dao.FlightDao;
import com.thanhtd.flight.storage.exception.LogicException;
import com.thanhtd.flight.storage.model.Flight;
import com.thanhtd.flight.storage.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new LinkedList<>();
        Iterable<Flight> iterable = flightDao.findAll();
        for (Flight flight : iterable) {
            flights.add(flight);
        }
        return flights;
    }

    @Override
    public Flight findByFlightId(String flightId) throws LogicException {
        return flightDao.findByFlightId(flightId);
    }

    @Override
    public Flight createFlight(Flight flight) {
        return flightDao.save(flight);
    }

    @Override
    public List<Flight> createMany(List<Flight> flights) {
        List<Flight> result = new LinkedList<>();
        for (Flight flight: flights) {
            flight = createFlight(flight);
            if (!ObjectUtils.isEmpty(flight))
                result.add(flight);
        }
        return result;
    }
}
