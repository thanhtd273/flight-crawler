package com.thanhtd.flight.crawler.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.flight.crawler.base.APIResponse;
import com.thanhtd.flight.crawler.constant.DataStatus;
import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dao.CityDao;
import com.thanhtd.flight.crawler.dto.CityDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.City;
import com.thanhtd.flight.crawler.service.CityService;
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
public class CityServiceImpl implements CityService {

    @Value("${aviationstack.api.url}")
    private String apiUrl;

    @Value("${aviationstack.api.access.key}")
    private String apiAccessKey;

    private final CityDao cityDao;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<City> getAllCities() {
        return cityDao.findAll();
    }

    @Override
    public City findByCityId(UUID cityId) throws LogicException {
        if (ObjectUtils.isEmpty(cityId))
            throw new LogicException(ErrorCode.ID_NULL);
        return cityDao.findByCityId(cityId);
    }

    @Override
    public City findByCode(String code) {
        return cityDao.findByCode(code);
    }

    @Override
    public City createCity(CityDTO cityDTO) throws LogicException {
        if (ObjectUtils.isEmpty(cityDTO)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        City city = new City();
        city.setCityId(UUID.randomUUID());
        city.setName(cityDTO.getCityName());
        city.setCode(cityDTO.getIATACode());
        city.setCountryName(cityDTO.getCityName());
        city.setStatus(DataStatus.ACTIVE);
        city.setCreatedAt(new Date(System.currentTimeMillis()));

        return cityDao.save(city);
    }

    @Override
    public List<City> crawlData() throws LogicException {
        // Call API
        int offset = 0;
        int limit = 10;
        String url = String.format("%s/cities?access_key=%s&offset=%d&limit=%d", apiUrl, apiAccessKey, offset, limit);
        APIResponse apiResponse = restTemplate.getForObject(url, APIResponse.class);
        if (ObjectUtils.isEmpty(apiResponse) || ObjectUtils.isEmpty(apiResponse.getData())) {
            throw new LogicException(ErrorCode.DATA_NULL, "Fetch data from aviation stack return null");
        }
        List<CityDTO> rawCities = objectMapper.convertValue(apiResponse.getData(), new TypeReference<List<CityDTO>>() {});

        // Save data on ScyllaDB
        return saveManyCities(rawCities);
    }

    @Override
    public List<City> importCityFromJsonFile() throws IOException, LogicException {
        String filePath = "cities.json";
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        List<CityDTO> rawCities = objectMapper.readValue(inputStream, new TypeReference<List<CityDTO>>() {});

        log.info("Size of raw airports from file {} is {}", filePath, rawCities.size());
        return saveManyCities(rawCities);
    }

    @Override
    public ErrorCode deleteCity(UUID cityId) {
        if (ObjectUtils.isEmpty(cityId)) {
            return ErrorCode.ID_NULL;
        }
        City city = cityDao.findByCityId(cityId);
        if (ObjectUtils.isEmpty(city))
            return ErrorCode.NOT_FOUND_CITY;
        cityDao.delete(city);
        return ErrorCode.SUCCESS;
    }

    private List<City> saveManyCities(List<CityDTO> rawCities) throws LogicException {
        List<City> cities = new LinkedList<>();
        for (CityDTO rawCity: rawCities) {
            City city = createCity(rawCity);
            cities.add(city);
        }
        return cities;
    }
}
