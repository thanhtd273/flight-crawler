package com.thanhtd.flight.storage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.flight.storage.model.Flight;
import com.thanhtd.flight.storage.service.FlightService;
import com.thanhtd.flight.storage.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    private static final String TOPIC = "flight_crawler_data";

    private static final String GROUP = "flight_group";

    private final ObjectMapper objectMapper;

    private final FlightService flightService;

    @Override
    @KafkaListener(topics = TOPIC, groupId = GROUP)
    public void consume(@Payload String message) throws JsonProcessingException {
        logger.info("Flight received: {}", message);
        List<Flight> flights = objectMapper.readValue(message, new TypeReference<List<Flight>>() {});
        List<Flight> savedFlights = flightService.createMany(flights);
        logger.info("Successfully ave {}/{} flights to Elasticsearch", savedFlights.size(), flights.size());
    }

}
