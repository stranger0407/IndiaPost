package com.indiapost.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for a consignment tracking result.
 * <p>
 * Contains the tracking number, identified service type,
 * current status, tracking history events, and a direct
 * link to the official India Post tracking portal.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResultDto {

    /** The consignment tracking number (e.g., "EE123456789IN") */
    private String trackingNumber;

    /** Identified service type (e.g., "Speed Post", "Registered Post") */
    private String serviceType;

    /** Current status of the consignment */
    private String currentStatus;

    /** Origin post office name */
    private String origin;

    /** Destination post office name */
    private String destination;

    /** Booking date in ISO format */
    private String bookingDate;

    /** Expected delivery date (estimation) */
    private String expectedDelivery;

    /** Weight in grams */
    private String weight;

    /** Direct link to official India Post tracking portal */
    private String officialTrackingUrl;

    /** Chronological list of tracking events */
    private List<TrackingEventDto> events;

    /** Additional note about tracking data source */
    private String note;
}
