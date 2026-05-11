package com.indiapost.postoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a single post office branch.
 * <p>
 * Maps to the JSON structure returned by the Postal PIN Code API
 * ({@code api.postalpincode.in}). Uses {@code @JsonIgnoreProperties}
 * to gracefully handle extra fields (e.g., "Block") from the API.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostOfficeDto {

    /** Post office branch name (e.g., "Baroda House") */
    @JsonProperty("Name")
    private String name;

    /** Optional description of the post office */
    @JsonProperty("Description")
    private String description;

    /** Branch type: Head, Sub, or Branch Post Office */
    @JsonProperty("BranchType")
    private String branchType;

    /** Delivery status: "Delivery" or "Non-Delivery" */
    @JsonProperty("DeliveryStatus")
    private String deliveryStatus;

    /** Postal circle (e.g., "New Delhi") */
    @JsonProperty("Circle")
    private String circle;

    /** District name */
    @JsonProperty("District")
    private String district;

    /** Division name */
    @JsonProperty("Division")
    private String division;

    /** Region name */
    @JsonProperty("Region")
    private String region;

    /** State name */
    @JsonProperty("State")
    private String state;

    /** Country — always "India" */
    @JsonProperty("Country")
    private String country;

    /** PIN code (added during processing) */
    @JsonProperty("Pincode")
    private String pincode;
}
