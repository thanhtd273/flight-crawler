package com.thanhtd.flight.crawler.config;

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    private static final int MAX_RETRY = 3;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        restTemplate.setErrorHandler(new HttpResponseErrorHandler());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(converter);
        restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
            if(httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });

        return restTemplate;
    }

    @Bean
    public CustomClientHttpRequestInterceptor customClientHttpRequestInterceptor() {
        return new CustomClientHttpRequestInterceptor();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        long timeout = 5;
        int readTimeout = 5;

        // Connect timeout
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(timeout * 1000))
                .build();
        // Connection Request Timeout
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(timeout * 2000))
                .build();

        // Socket Config
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofMilliseconds(timeout * 1000))
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setDefaultSocketConfig(socketConfig);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictExpiredConnections()
                .evictIdleConnections(Timeout.ofMilliseconds(5))
                .setRetryStrategy(new CustomHttpRequestRetryStrategy())
                .build();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // Read timeout
        clientHttpRequestFactory.setReadTimeout(readTimeout * 3000);

        return clientHttpRequestFactory;
    }

    private static class CustomHttpRequestRetryStrategy implements HttpRequestRetryStrategy {
        @Override
        public boolean retryRequest(HttpRequest httpRequest, IOException e, int executionCount, HttpContext httpContext) {
            return executionCount <= 5;
        }

        @Override
        public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
            if (executionCount > MAX_RETRY)
                return false;
            try {
                int responseCode = httpResponse.getCode();
                if (responseCode >= 500 && responseCode < 600) {
                    logger.info("Retrying for 5xx code");
                    return true;
                } else if (responseCode == 429) {
                    logger.error("Too many requests, retrying ...");
                    return true;
                }
            } catch (Exception e) {
                logger.error("Retry http request error: {}", e.getMessage());
            }
            return false;
        }

        @Override
        public TimeValue getRetryInterval(HttpResponse httpResponse, int i, HttpContext httpContext) {
            return Timeout.ofMilliseconds(5);
        }
    }

}
