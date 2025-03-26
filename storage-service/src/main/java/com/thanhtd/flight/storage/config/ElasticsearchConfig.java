package com.thanhtd.flight.storage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

//    @Value("${spring.data.elasticsearch.username}")
//    private String username;
//
//    @Value("${spring.data.elasticsearch.password}")
//    private String password;

    @Value("${spring.data.elasticsearch.connection-timeout}")
    private long connectionTimeout;

    @Value("${spring.data.elasticsearch.socket-timeout}")
    private long socketTimeout;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(clusterNodes)
//                .withBasicAuth(username, password)
                .withConnectTimeout(Duration.ofMillis(connectionTimeout))
                .withSocketTimeout(Duration.ofSeconds(socketTimeout))
                .build();
    }
}
