package com.indiapost.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration for downstream service communication.
 * <p>
 * Provides pre-configured {@link WebClient} beans for each
 * downstream microservice so that controllers can inject them
 * directly without worrying about base URLs.
 * </p>
 */
@Configuration
public class WebClientConfig {

    @Value("${gateway.services.postoffice-url}")
    private String postOfficeServiceUrl;

    @Value("${gateway.services.tracking-url}")
    private String trackingServiceUrl;

    /**
     * WebClient targeting the Post Office microservice.
     */
    @Bean(name = "postOfficeWebClient")
    public WebClient postOfficeWebClient() {
        return WebClient.builder()
                .baseUrl(postOfficeServiceUrl)
                .build();
    }

    /**
     * WebClient targeting the Tracking microservice.
     */
    @Bean(name = "trackingWebClient")
    public WebClient trackingWebClient() {
        return WebClient.builder()
                .baseUrl(trackingServiceUrl)
                .build();
    }
}
