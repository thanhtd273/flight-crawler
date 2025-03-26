package com.thanhtd.flight.crawler.service.impl;

import com.thanhtd.flight.crawler.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private static final String TOPIC = "test_topic";

    private final KafkaTemplate<String, String> flightKafkaTemplate;

    @Override
    public void sendMessage( String flight) {
        flightKafkaTemplate.send(TOPIC, flight);
        logger.info("Message sent: {}", flight);
    }
}
