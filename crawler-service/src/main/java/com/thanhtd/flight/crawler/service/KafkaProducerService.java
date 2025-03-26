package com.thanhtd.flight.crawler.service;


public interface KafkaProducerService {
    void sendMessage(String flight);
}
