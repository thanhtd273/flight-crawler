package com.thanhtd.flight.crawler.service;

import com.thanhtd.flight.crawler.constant.ErrorCode;
import com.thanhtd.flight.crawler.dto.CityDTO;
import com.thanhtd.flight.crawler.exception.LogicException;
import com.thanhtd.flight.crawler.model.City;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CityService {
    List<City> getAllCities();

    City findByCityId(UUID cityId) throws LogicException;

    City findByCode(String code);

    City createCity(CityDTO cityDTO) throws LogicException;

    List<City> crawlData() throws LogicException;

    List<City> importCityFromJsonFile() throws IOException, LogicException;

    ErrorCode deleteCity(UUID cityId);
}
