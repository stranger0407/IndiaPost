package com.indiapost.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Gateway controller that proxies Tracking requests
 * to the downstream Tracking microservice.
 * <p>
 * All endpoints are prefixed with {@code /api/tracking}.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/tracking")
public class TrackingGatewayController {

    private final WebClient trackingWebClient;

    /**
     * Constructor — injects the Tracking-specific WebClient.
     *
     * @param trackingWebClient WebClient configured for the Tracking service
     */
    public TrackingGatewayController(
            @Qualifier("trackingWebClient") WebClient trackingWebClient) {
        this.trackingWebClient = trackingWebClient;
    }

    /**
     * Track a consignment by its tracking number.
     *
     * @param trackingNumber 13-character alphanumeric consignment ID
     * @return JSON response with tracking status and history
     */
    @GetMapping(value = "/{trackingNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> trackConsignment(@PathVariable String trackingNumber) {
        log.info("Gateway → Tracking Service: track consignment={}", trackingNumber);
        return trackingWebClient.get()
                .uri("/api/v1/tracking/{trackingNumber}", trackingNumber)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Get supported tracking service types.
     *
     * @return JSON array of service types (Speed Post, Registered, etc.)
     */
    @GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getServiceTypes() {
        log.info("Gateway → Tracking Service: get service types");
        return trackingWebClient.get()
                .uri("/api/v1/tracking/services")
                .retrieve()
                .bodyToMono(String.class);
    }
}
