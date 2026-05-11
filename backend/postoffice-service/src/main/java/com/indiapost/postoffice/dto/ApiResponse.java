package com.indiapost.postoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Standardized API response wrapper for the Post Office service.
 * <p>
 * All endpoints return this structure to ensure consistent
 * response format for the frontend.
 * </p>
 *
 * @param <T> type of the payload data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /** Whether the request was successful */
    private boolean success;

    /** Human-readable status message */
    private String message;

    /** Number of results found */
    private int count;

    /** Payload data */
    private T data;

    /**
     * Factory method for successful responses.
     *
     * @param data    the payload
     * @param message descriptive message
     * @param count   number of results
     * @param <T>     data type
     * @return a success {@link ApiResponse}
     */
    public static <T> ApiResponse<T> success(T data, String message, int count) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .count(count)
                .data(data)
                .build();
    }

    /**
     * Factory method for error responses.
     *
     * @param message error description
     * @param <T>     data type
     * @return an error {@link ApiResponse}
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .count(0)
                .data(null)
                .build();
    }
}
