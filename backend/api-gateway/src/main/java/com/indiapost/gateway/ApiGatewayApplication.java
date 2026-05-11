package com.indiapost.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway — Entry point for all client requests.
 * <p>
 * This service acts as a reverse proxy, routing requests from the
 * React frontend to the appropriate downstream microservices:
 * <ul>
 *   <li>/api/postoffice/** → Post Office Service (port 8081)</li>
 *   <li>/api/tracking/**   → Tracking Service   (port 8082)</li>
 * </ul>
 * </p>
 *
 * @author India Post Portal Team
 * @version 1.0.0
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
