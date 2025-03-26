package com.thanhtd.flight.crawler.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.DataStatus;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dao.AirlineDao;
import com.thanhtd.flight.crawler.dto.AirlineDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.Airline;
import com.thanhtd.flight.crawler.service.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
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
@Slf4j
public class AirlineServiceImpl implements AirlineService {

    @Value("${aviationstack.api.url}")
    private String apiUrl;

    @Value("${aviationstack.api.access.key}")
    private String apiAccessKey;

    private final AirlineDao airlineDao;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<Airline> getAllAirlines() {
        return airlineDao.findAll();
    }

    @Override
    public Airline findByAirlineId(UUID airlineId) throws LogicException {
        if (ObjectUtils.isEmpty(airlineId))
            throw new LogicException(ErrorCode.ID_NULL);
        return airlineDao.findByAirlineId(airlineId);
    }

    @Override
    public Airline createAirline(AirlineDTO airlineDTO) throws LogicException {
        if (ObjectUtils.isEmpty(airlineDTO))
            throw new LogicException(ErrorCode.DATA_NULL);
        Airline airline = new Airline();
        airline.setAirlineId(UUID.randomUUID());
        airline.setName(airlineDTO.getAirlineName());
        airline.setCode(airlineDTO.getIATACode());
        airline.setStatus(DataStatus.ACTIVE);
        airline.setCountryName(airlineDTO.getCountryName());
        airline.setCreatedAt(new Date(System.currentTimeMillis()));

        return airlineDao.save(airline);
    }

    @Override
    public List<Airline> crawlData() throws LogicException {
        // Call API
        int offset = 0;
        int limit = 10;
        String url = String.format("%s/airlines?access_key=%s&offset=%d&limit=%d", apiUrl, apiAccessKey, offset, limit);
        APIResponse apiResponse = restTemplate.getForObject(url, APIResponse.class);
        if (ObjectUtils.isEmpty(apiResponse) || ObjectUtils.isEmpty(apiResponse.getData())) {
            throw new LogicException(ErrorCode.DATA_NULL, "Fetch airline data from aviation stack return null");
        }
        List<AirlineDTO> rawAirlines = objectMapper.convertValue(apiResponse.getData(), new TypeReference<List<AirlineDTO>>() {});

        // Save data on ScyllaDB
        return saveManyAirlines(rawAirlines);
    }

    @Override
    public List<Airline> importAirlineFromJsonFile() throws IOException, LogicException {
        String filePath = "airlines.json";
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        List<AirlineDTO> rawAirlines = objectMapper.readValue(inputStream, new TypeReference<List<AirlineDTO>>() {});

        log.info("Size of raw airports from file {} is {}", filePath, rawAirlines.size());
        return saveManyAirlines(rawAirlines);
    }

    private List<Airline> saveManyAirlines(List<AirlineDTO> rawAirlines) throws LogicException {
        List<Airline> airlines = new LinkedList<>();
        for (AirlineDTO rawAirline: rawAirlines) {
            Airline airline = createAirline(rawAirline);
            airlines.add(airline);
        }
        return airlines;
    }
}
