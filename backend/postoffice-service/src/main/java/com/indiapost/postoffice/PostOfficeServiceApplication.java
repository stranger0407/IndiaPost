package com.indiapost.postoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Post Office Microservice — Provides lookup capabilities for
 * Indian post offices via the open-source Postal PIN Code API.
 * <p>
 * Features:
 * <ul>
 *   <li>Search post offices by 6-digit PIN code</li>
 *   <li>Search post offices by branch name</li>
 *   <li>Get list of states for filtering</li>
 *   <li>Response caching for frequently queried PIN codes</li>
 * </ul>
 * </p>
 *
 * @author India Post Portal Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
public class PostOfficeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostOfficeServiceApplication.class, args);
    }
}
