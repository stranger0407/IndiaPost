package com.indiapost.tracking.service;

import com.indiapost.tracking.dto.ServiceTypeDto;
import com.indiapost.tracking.dto.TrackingEventDto;
import com.indiapost.tracking.dto.TrackingResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Service layer for consignment tracking operations.
 * <p>
 * Since India Post does not expose a public tracking API,
 * this service provides:
 * <ul>
 *   <li>Tracking number validation and service type identification</li>
 *   <li>Official tracking portal URL generation</li>
 *   <li>Demo tracking data for testing and demonstration</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Note:</strong> For production use, this service would need
 * to integrate with India Post's official business API (requires
 * partnership agreement) or use authorized third-party tracking APIs.
 * </p>
 */
@Slf4j
@Service
public class TrackingService {

    /**
     * Standard India Post international tracking number format: 2 letters + 9 digits + 2 letters.
     * Example: EE123456789IN, RR987654321IN
     */
    private static final Pattern INTERNATIONAL_TRACKING_PATTERN = Pattern.compile("^[A-Z]{2}\\d{9}[A-Z]{2}$");

    /**
     * Domestic/general tracking number patterns accepted by India Post.
     * These include purely numeric IDs, alphanumeric IDs of various lengths.
     * Minimum 8 characters, maximum 30 characters, letters and digits only.
     */
    private static final Pattern GENERAL_TRACKING_PATTERN = Pattern.compile("^[A-Z0-9]{8,30}$");

    /** Official India Post tracking URL template */
    private static final String OFFICIAL_TRACKING_URL =
            "https://www.indiapost.gov.in/VAS/Pages/trackconsignment.aspx";

    /** Date formatter for display */
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    /**
     * Mapping of tracking number prefixes to service types.
     * <p>
     * India Post uses standardized 2-letter prefixes:
     * <ul>
     *   <li>EE - Speed Post (Express)</li>
     *   <li>RR - Registered Post</li>
     *   <li>EM - EMS (International Express Mail)</li>
     *   <li>CP - Parcel Post</li>
     *   <li>CD - Cash on Delivery</li>
     *   <li>RB - Registered Book Post</li>
     * </ul>
     * </p>
     */
    private static final Map<String, String> PREFIX_SERVICE_MAP = Map.of(
            "EE", "Speed Post",
            "RR", "Registered Post",
            "EM", "EMS (International Express Mail)",
            "CP", "Parcel Post",
            "CD", "Cash on Delivery",
            "RB", "Registered Book Post",
            "VP", "Value Payable Post",
            "RI", "Registered International"
    );

    /**
     * Track a consignment by its tracking number.
     * <p>
     * Validates the tracking number format, identifies the service type,
     * generates the official tracking URL, and provides demo tracking data.
     * </p>
     *
     * @param trackingNumber 13-character alphanumeric tracking ID
     * @return tracking result with status, events, and official URL
     * @throws IllegalArgumentException if tracking number format is invalid
     */
    public TrackingResultDto trackConsignment(String trackingNumber) {
        String normalized = trackingNumber.toUpperCase().trim();
        log.info("Tracking consignment: {}", normalized);

        // Validate tracking number format — accept international or general format
        boolean isInternationalFormat = INTERNATIONAL_TRACKING_PATTERN.matcher(normalized).matches();
        boolean isValidFormat = isInternationalFormat || GENERAL_TRACKING_PATTERN.matcher(normalized).matches();

        if (!isValidFormat) {
            throw new IllegalArgumentException(
                    "Invalid tracking number format. Please enter a valid India Post tracking number " +
                    "(e.g., EE123456789IN for Speed Post, or your numeric consignment ID). " +
                    "Minimum 8 characters, letters and digits only."
            );
        }

        // Identify service type from prefix (only for international format)
        String serviceType = "India Post Consignment";
        if (isInternationalFormat) {
            String prefix = normalized.substring(0, 2);
            serviceType = PREFIX_SERVICE_MAP.getOrDefault(prefix, "India Post Consignment");
        }

        // Generate demo tracking events
        List<TrackingEventDto> events = generateDemoTrackingEvents(normalized, serviceType);

        return TrackingResultDto.builder()
                .trackingNumber(normalized)
                .serviceType(serviceType)
                .currentStatus(events.get(events.size() - 1).getStatus())
                .origin("New Delhi GPO")
                .destination("Mumbai GPO")
                .bookingDate(LocalDateTime.now().minusDays(3).format(DISPLAY_FORMAT))
                .expectedDelivery(LocalDateTime.now().plusDays(2).format(DISPLAY_FORMAT))
                .weight("250g")
                .officialTrackingUrl(OFFICIAL_TRACKING_URL)
                .events(events)
                .note("This is demonstration data. For real-time tracking, please visit the official India Post tracking portal using the link provided above.")
                .build();
    }

    /**
     * Get all supported postal service types.
     *
     * @return list of service type DTOs with descriptions
     */
    public List<ServiceTypeDto> getServiceTypes() {
        return List.of(
                ServiceTypeDto.builder()
                        .name("Speed Post")
                        .prefix("EE")
                        .description("Express delivery service with guaranteed time-bound delivery and real-time tracking")
                        .estimatedDelivery("1-3 days (metro), 2-5 days (non-metro)")
                        .build(),
                ServiceTypeDto.builder()
                        .name("Registered Post")
                        .prefix("RR")
                        .description("Secure postal service with proof of delivery and tracking facility")
                        .estimatedDelivery("3-7 days")
                        .build(),
                ServiceTypeDto.builder()
                        .name("EMS (International)")
                        .prefix("EM")
                        .description("International express mail service for fast overseas delivery")
                        .estimatedDelivery("5-10 days (international)")
                        .build(),
                ServiceTypeDto.builder()
                        .name("Parcel Post")
                        .prefix("CP")
                        .description("Cost-effective service for sending parcels across India")
                        .estimatedDelivery("7-14 days")
                        .build(),
                ServiceTypeDto.builder()
                        .name("Cash on Delivery")
                        .prefix("CD")
                        .description("Send articles with payment collected on delivery")
                        .estimatedDelivery("5-10 days")
                        .build(),
                ServiceTypeDto.builder()
                        .name("Registered Book Post")
                        .prefix("RB")
                        .description("Economical option for sending books and printed materials")
                        .estimatedDelivery("7-14 days")
                        .build()
        );
    }

    /**
     * Generates realistic demo tracking events for demonstration.
     *
     * @param trackingNumber the consignment tracking number
     * @param serviceType    identified service type
     * @return chronologically ordered list of tracking events
     */
    private List<TrackingEventDto> generateDemoTrackingEvents(String trackingNumber, String serviceType) {
        LocalDateTime now = LocalDateTime.now();
        List<TrackingEventDto> events = new ArrayList<>();

        events.add(TrackingEventDto.builder()
                .timestamp(now.minusDays(3).format(DISPLAY_FORMAT))
                .location("New Delhi GPO (110001)")
                .status("Item Booked")
                .description("Article booked at counter — " + serviceType)
                .build());

        events.add(TrackingEventDto.builder()
                .timestamp(now.minusDays(3).plusHours(4).format(DISPLAY_FORMAT))
                .location("New Delhi NSH (110006)")
                .status("Dispatched")
                .description("Article dispatched from booking office to sorting hub")
                .build());

        events.add(TrackingEventDto.builder()
                .timestamp(now.minusDays(2).format(DISPLAY_FORMAT))
                .location("Delhi RMS Transit (110001)")
                .status("In Transit")
                .description("Article received at Railway Mail Service transit facility")
                .build());

        events.add(TrackingEventDto.builder()
                .timestamp(now.minusDays(1).format(DISPLAY_FORMAT))
                .location("Mumbai RMS Transit (400001)")
                .status("In Transit")
                .description("Article arrived at destination city sorting facility")
                .build());

        events.add(TrackingEventDto.builder()
                .timestamp(now.minusHours(6).format(DISPLAY_FORMAT))
                .location("Mumbai GPO (400001)")
                .status("Out for Delivery")
                .description("Article dispatched for delivery to recipient address")
                .build());

        return events;
    }
}
