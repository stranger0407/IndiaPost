package com.indiapost.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Tracking Microservice — Provides consignment tracking capabilities.
 * <p>
 * Since India Post does not offer a public tracking API, this service
 * provides:
 * <ul>
 *   <li>Tracking number format validation</li>
 *   <li>Service type classification (Speed Post, Registered, EMS, etc.)</li>
 *   <li>Direct link generation to the official India Post tracking portal</li>
 *   <li>Simulated tracking data for demonstration purposes</li>
 * </ul>
 * </p>
 *
 * @author India Post Portal Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
public class TrackingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingServiceApplication.class, args);
    }
}
