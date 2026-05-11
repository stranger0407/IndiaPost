package com.indiapost.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Gateway controller that proxies Post Office requests
 * to the downstream Post Office microservice.
 * <p>
 * All endpoints are prefixed with {@code /api/postoffice}.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/postoffice")
public class PostOfficeGatewayController {

    private final WebClient postOfficeWebClient;

    /**
     * Constructor — injects the Post Office-specific WebClient.
     *
     * @param postOfficeWebClient WebClient configured for the Post Office service
     */
    public PostOfficeGatewayController(
            @Qualifier("postOfficeWebClient") WebClient postOfficeWebClient) {
        this.postOfficeWebClient = postOfficeWebClient;
    }

    /**
     * Search post offices by PIN code.
     *
     * @param pincode 6-digit Indian postal PIN code
     * @return JSON response from the Post Office service
     */
    @GetMapping(value = "/pincode/{pincode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> searchByPincode(@PathVariable String pincode) {
        log.info("Gateway → PostOffice Service: search by pincode={}", pincode);
        return postOfficeWebClient.get()
                .uri("/api/v1/postoffice/pincode/{pincode}", pincode)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Search post offices by branch name.
     *
     * @param name post office branch name (partial match supported)
     * @return JSON response from the Post Office service
     */
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> searchByName(@RequestParam String name) {
        log.info("Gateway → PostOffice Service: search by name={}", name);
        return postOfficeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/postoffice/search")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Get list of all Indian states for filtering.
     *
     * @return JSON array of state names
     */
    @GetMapping(value = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getStates() {
        log.info("Gateway → PostOffice Service: get states");
        return postOfficeWebClient.get()
                .uri("/api/v1/postoffice/states")
                .retrieve()
                .bodyToMono(String.class);
    }
}
