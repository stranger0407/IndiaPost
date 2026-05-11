package com.indiapost.postoffice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Wrapper DTO for the Postal PIN Code API response.
 * <p>
 * The external API returns a JSON array, where each element
 * contains a status, message, and a list of post offices.
 * </p>
 *
 * <pre>
 * [
 *   {
 *     "Message": "Number of Post office(s) found: 2",
 *     "Status": "Success",
 *     "PostOffice": [ ... ]
 *   }
 * ]
 * </pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostalApiResponse {

    /** "Success" or "Error" */
    @JsonProperty("Status")
    private String status;

    /** Human-readable message (e.g., "Number of Post office(s) found: 3") */
    @JsonProperty("Message")
    private String message;

    /** List of matched post offices; null when status is "Error" */
    @JsonProperty("PostOffice")
    private List<PostOfficeDto> postOffice;
}
