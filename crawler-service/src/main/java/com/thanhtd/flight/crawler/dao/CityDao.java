package com.thanhtd.flight.crawler.dao;

import com.thanhtd.flight.crawler.model.City;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CityDao extends CassandraRepository<City, UUID> {
    City findByCityId(UUID cityId);

    City findByCode(String name);
}
