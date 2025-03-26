package com.thanhtd.flight.crawler.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.flight.crawler.constant.DataStatus;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dao.FlightDao;
import com.thanhtd.flight.crawler.dto.AviationHttpResponse;
import com.thanhtd.flight.crawler.dto.FlightAirportDTO;
import com.thanhtd.flight.crawler.dto.FlightDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Airport;
import com.thanhtd.flight.crawler.model.AirportLocation;
import com.thanhtd.flight.crawler.model.City;
import com.thanhtd.flight.crawler.model.Flight;
import com.thanhtd.flight.crawler.service.AirportService;
import com.thanhtd.flight.crawler.service.CityService;
import com.thanhtd.flight.crawler.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private static final String TOPIC = "flight_crawler_data";

    private static final String GROUP = "flight_group";

    @Value("${aviationstack.api.url}")
    private String apiUrl;

    @Value("${aviationstack.api.access.key}")
    private String apiAccessKey;

    private final FlightDao flightDao;

    private final RestTemplate restTemplate;

    private final CityService cityService;

    private final AirportService airportService;
    
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Flight flightByFlightId(UUID flightId) throws LogicException {
        if (ObjectUtils.isEmpty(flightId)) {
            throw new LogicException(ErrorCode.ID_NULL);
        }
        return flightDao.findByFlightId(flightId);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    @Override
    public Flight createFlight(FlightDTO flightDTO) throws LogicException {
        FlightAirportDTO departure = flightDTO.getDeparture();
        AirportLocation departureAirport = buildAirportLocation(departure);

        FlightAirportDTO arrival = flightDTO.getArrival();
        AirportLocation arrivalAirport = buildAirportLocation(arrival);

        Flight flight = new Flight();
        flight.setFlightId(UUID.randomUUID());
        flight.setFlightCode(flightDTO.getFlight().getIcao());
        flight.setDeparture(departureAirport);
        flight.setArrival(arrivalAirport);
        flight.setPrice(100);
        if (!ObjectUtils.isEmpty(arrivalAirport) && !ObjectUtils.isEmpty(arrivalAirport.getScheduled())
                && !ObjectUtils.isEmpty(departureAirport) && !ObjectUtils.isEmpty(departureAirport.getScheduled())) {
            flight.setDuration((int) (arrivalAirport.getScheduled().getTime() - departureAirport.getScheduled().getTime()));
        }
        flight.setCurrency("USD"); // TODO
        flight.setSeatsAvailable(100); // TODO
        if (!ObjectUtils.isEmpty(flightDTO.getAircraft()))
            flight.setAirplaneType(flightDTO.getAircraft().getIcao());
        flight.setFlightDate(flightDTO.getFlightDate());
        flight.setCreatedAt(new Date(System.currentTimeMillis()));
        flight.setStatus(DataStatus.ACTIVE);
        return flightDao.save(flight);
    }

    @Override
    public List<Flight> crawlData() throws LogicException, JsonProcessingException {
        // Call API
       int offset = 1;
       int limit = 100;
       String url = String.format("%s/flights?access_key=%s&offset=%d&limit=%d", apiUrl, apiAccessKey, offset, limit);
       AviationHttpResponse<FlightDTO> apiResponse = fetchFlightData(url);
       if (ObjectUtils.isEmpty(apiResponse) || ObjectUtils.isEmpty(apiResponse.getData())) {
           throw new LogicException(ErrorCode.DATA_NULL, "Fetch data from aviation stack return null");
       }
       List<FlightDTO> rawFlights = apiResponse.getData();
        // Save data on ScyllaDB
       List<Flight> flights = saveManyFlights(rawFlights);

        // Publish flights to kafka
        String message = objectMapper.writeValueAsString(flights);
        kafkaTemplate.send(TOPIC, message);
        return flights;
    }

    @Override
    public ErrorCode deleteFlight(String flightId) throws LogicException {
        return null;
    }

    private AirportLocation buildAirportLocation(FlightAirportDTO departure) {
        if (ObjectUtils.isEmpty(departure)) {
            return null;
        }
        AirportLocation airportLocation = new AirportLocation();
        String airportName = departure.getAirport();
        Airport airport = airportService.findByName(airportName);
        if (!ObjectUtils.isEmpty(airport)) {
            City city = cityService.findByCode(airport.getCityCode());
            if (!ObjectUtils.isEmpty(city)) {
                airportLocation.setCity(city.getName());
                airportLocation.setCountry(city.getCountryName());
            }
        }
        airportLocation.setAirportName(airportName);
        airportLocation.setAirportCode(departure.getIata());
        airportLocation.setScheduled(departure.getScheduled());

        return airportLocation;
    }

    private AviationHttpResponse<FlightDTO> fetchFlightData(String url) {
        ResponseEntity<AviationHttpResponse<FlightDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<AviationHttpResponse<FlightDTO>>() {}
        );

        return response.getBody();
    }

    private List<Flight> saveManyFlights(List<FlightDTO> rawFlights) throws LogicException {
        List<Flight> flights = new LinkedList<>();
        for (FlightDTO flightDTO: rawFlights) {
            Flight flight = createFlight(flightDTO);
            flights.add(flight);
        }
        return flights;
    }
}
