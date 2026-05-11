package com.indiapost.postoffice.controller;

import com.indiapost.postoffice.dto.ApiResponse;
import com.indiapost.postoffice.dto.PostOfficeDto;
import com.indiapost.postoffice.service.PostOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Post Office lookup operations.
 * <p>
 * Provides endpoints for:
 * <ul>
 *   <li>PIN code-based post office search</li>
 *   <li>Branch name-based post office search</li>
 *   <li>State/UT listing for filter dropdowns</li>
 * </ul>
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/postoffice")
@RequiredArgsConstructor

@Tag(name = "Post Office", description = "Post office lookup and search operations")
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    /**
     * Search post offices by 6-digit PIN code.
     *
     * @param pincode exactly 6 digits (e.g., "110001")
     * @return list of post offices in the given PIN code area
     */
    @Operation(
            summary = "Search by PIN code",
            description = "Returns all post offices matching the given 6-digit Indian postal PIN code"
    )
    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<ApiResponse<List<PostOfficeDto>>> searchByPincode(
            @Parameter(description = "6-digit Indian PIN code", example = "110001")
            @PathVariable String pincode) {

        // Manual PIN code format validation
        if (pincode == null || !pincode.matches("^[1-9][0-9]{5}$")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("PIN code must be exactly 6 digits starting with a non-zero digit"));
        }

        log.info("POST OFFICE: Search by pincode={}", pincode);
        List<PostOfficeDto> results = postOfficeService.searchByPincode(pincode);

        if (results.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error("No post offices found for PIN code: " + pincode));
        }

        return ResponseEntity.ok(
                ApiResponse.success(results, "Post offices found for PIN code: " + pincode, results.size())
        );
    }

    /**
     * Search post offices by branch name.
     *
     * @param name post office branch name (minimum 3 characters)
     * @return list of matching post offices
     */
    @Operation(
            summary = "Search by branch name",
            description = "Returns post offices matching the given branch name (partial match)"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostOfficeDto>>> searchByName(
            @Parameter(description = "Post office branch name", example = "New Delhi")
            @RequestParam String name) {

        if (name == null || name.trim().length() < 3) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Search query must be at least 3 characters long"));
        }

        log.info("POST OFFICE: Search by name='{}'", name);
        List<PostOfficeDto> results = postOfficeService.searchByName(name.trim());

        if (results.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error("No post offices found matching: " + name));
        }

        return ResponseEntity.ok(
                ApiResponse.success(results, "Post offices found matching: " + name, results.size())
        );
    }

    /**
     * Get list of all Indian states and union territories.
     *
     * @return sorted list of state/UT names
     */
    @Operation(
            summary = "List all states",
            description = "Returns all Indian states and union territories for filter dropdowns"
    )
    @GetMapping("/states")
    public ResponseEntity<ApiResponse<List<String>>> getStates() {
        List<String> states = postOfficeService.getAllStates();
        return ResponseEntity.ok(
                ApiResponse.success(states, "All Indian states and union territories", states.size())
        );
    }
}
