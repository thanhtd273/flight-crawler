package com.thanhtd.flight.crawler.config;

import io.netty.channel.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class HttpResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().value() == 606 ||
                response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        String statusText = response.getStatusText();
        logger.error("API call failed with status: {} - {}", statusCode.value(), statusText);
        if(response.getStatusCode().value() == 606 ){
            throw new ConnectTimeoutException();
        }
    }
}
