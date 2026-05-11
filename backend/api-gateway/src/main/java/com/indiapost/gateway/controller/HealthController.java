package com.indiapost.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * Health-check endpoint for the API Gateway.
 * <p>
 * Returns a simple JSON object indicating the gateway is alive,
 * along with the current server timestamp.
 * </p>
 */
@RestController
public class HealthController {

    /**
     * Health check — used by frontend and load balancers.
     *
     * @return status map with "UP" and current timestamp
     */
    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "api-gateway",
                "timestamp", Instant.now().toString()
        );
    }
}
