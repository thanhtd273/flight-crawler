package com.thanhtd.flight.crawler.dao;

import com.thanhtd.flight.crawler.model.Airplane;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirplaneDao extends CassandraRepository<Airplane, UUID> {

}
