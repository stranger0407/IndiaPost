package com.indiapost.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a postal service type with its tracking prefix and description.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeDto {

    /** Service type name (e.g., "Speed Post") */
    private String name;

    /** Tracking number prefix pattern (e.g., "EE") */
    private String prefix;

    /** Description of the service */
    private String description;

    /** Estimated delivery time */
    private String estimatedDelivery;
}
