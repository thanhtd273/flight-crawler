package com.thanhtd.flight.crawler.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.flight.crawler.constant.DataStatus;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dao.AirportDao;
import com.thanhtd.flight.crawler.dto.AirportDTO;
import com.thanhtd.flight.crawler.dto.AviationHttpResponse;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Airport;
import com.thanhtd.flight.crawler.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private static final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);
    @Value("${aviationstack.api.url}")
    private String apiUrl;

    @Value("${aviationstack.api.access.key}")
    private String apiAccessKey;

    private final AirportDao airportDao;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<Airport> getAllAirports() {
        return airportDao.findAll();
    }

    @Override
    public Airport findByAirportId(UUID airportId) throws LogicException {
        if (ObjectUtils.isEmpty(airportId))
            throw new LogicException(ErrorCode.ID_NULL);
        return airportDao.findByAirportId(airportId);
    }

    @Override
    public Airport findByName(String name) {
        return airportDao.findByName(name);
    }

    @Override
    public Airport createAirport(AirportDTO airportDTO) throws LogicException {
        if (ObjectUtils.isEmpty(airportDTO))
            throw new LogicException(ErrorCode.DATA_NULL);
        Airport airport = new Airport();
        airport.setAirportId(UUID.randomUUID());
        airport.setName(airportDTO.getAirportName());
        airport.setCode(airportDTO.getIATACode());
        airport.setStatus(DataStatus.ACTIVE);
        airport.setCityCode(airportDTO.getCityIATACode());
        airport.setCountryName(airportDTO.getCountryName());
        airport.setCreatedAt(new Date(System.currentTimeMillis()));

        return airportDao.save(airport);
    }

    @Override
    public List<Airport> crawlData() throws LogicException {
        // Call API
        int offset = 0;
        int limit = 10;
        String url = String.format("%s/airports?access_key=%s&offset=%d&limit=%d", apiUrl, apiAccessKey, offset, limit);
        AviationHttpResponse<AirportDTO> response = fetchAirportData(url);
        if (ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())) {
            throw new LogicException(ErrorCode.DATA_NULL, "Fetch airport data from aviation stack return null");
        }
        log.info("All airports of AviationStack are {}", response.getPagination().getTotal());
        List<AirportDTO> rawAirports = objectMapper.convertValue(response.getData(), new TypeReference<List<AirportDTO>>() {});

        // Save data on ScyllaDB
        return saveManyAirports(rawAirports);
    }

    @Override
    public List<Airport> importAirportFromJsonFile(String path) throws IOException, LogicException {
        InputStream inputStream = new ClassPathResource("airports.json").getInputStream();
        List<AirportDTO> rawAirports = objectMapper.readValue(inputStream, new TypeReference<List<AirportDTO>>() {});
        
        log.info("Size of raw airports from file {} is {}", path, rawAirports.size());
        return saveManyAirports(rawAirports);
    }

    private AviationHttpResponse<AirportDTO> fetchAirportData(String url) {
        ResponseEntity<AviationHttpResponse<AirportDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<AviationHttpResponse<AirportDTO>>() {}
        );

        return response.getBody();
    }
    
    private List<Airport> saveManyAirports(List<AirportDTO> rawAirports) throws LogicException {
        List<Airport> airports = new LinkedList<>();
        for (AirportDTO rawAirport: rawAirports) {
            Airport airport = createAirport(rawAirport);
            airports.add(airport);
        }
        return airports;
    }
}
