package com.indiapost.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single tracking event in the consignment journey.
 * <p>
 * Each event captures a point-in-time status update with
 * location, timestamp, and description.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventDto {

    /** ISO timestamp of the event */
    private String timestamp;

    /** Location/office where the event occurred */
    private String location;

    /** Status description (e.g., "Item Received", "In Transit") */
    private String status;

    /** Detailed description of the event */
    private String description;
}
