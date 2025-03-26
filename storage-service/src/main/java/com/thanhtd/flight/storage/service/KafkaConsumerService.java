package com.thanhtd.flight.storage.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void consume(String flight) throws JsonProcessingException;
}
