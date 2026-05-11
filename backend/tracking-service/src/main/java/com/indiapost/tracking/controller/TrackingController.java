package com.indiapost.tracking.controller;

import com.indiapost.tracking.dto.ApiResponse;
import com.indiapost.tracking.dto.ServiceTypeDto;
import com.indiapost.tracking.dto.TrackingResultDto;
import com.indiapost.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for consignment tracking operations.
 * <p>
 * Provides endpoints for tracking consignments and listing
 * supported postal service types.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
@Tag(name = "Tracking", description = "Consignment tracking and service type operations")
public class TrackingController {

    private final TrackingService trackingService;

    /**
     * Track a consignment by its tracking number.
     *
     * @param trackingNumber 13-character alphanumeric tracking ID (e.g., EE123456789IN)
     * @return tracking result with status, events, and official portal link
     */
    @Operation(
            summary = "Track consignment",
            description = "Returns tracking details for the given consignment number with status history and official tracking link"
    )
    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ApiResponse<TrackingResultDto>> trackConsignment(
            @Parameter(description = "13-character tracking number", example = "EE123456789IN")
            @PathVariable String trackingNumber) {

        log.info("TRACKING: Track consignment={}", trackingNumber);

        try {
            TrackingResultDto result = trackingService.trackConsignment(trackingNumber);
            return ResponseEntity.ok(
                    ApiResponse.success(result, "Tracking information retrieved successfully")
            );
        } catch (IllegalArgumentException e) {
            log.warn("Invalid tracking number: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get all supported postal service types.
     *
     * @return list of service types with descriptions and estimated delivery times
     */
    @Operation(
            summary = "List service types",
            description = "Returns all supported India Post service types with tracking prefixes and delivery estimates"
    )
    @GetMapping("/services")
    public ResponseEntity<ApiResponse<List<ServiceTypeDto>>> getServiceTypes() {
        List<ServiceTypeDto> serviceTypes = trackingService.getServiceTypes();
        return ResponseEntity.ok(
                ApiResponse.success(serviceTypes, "Available India Post service types")
        );
    }
}
