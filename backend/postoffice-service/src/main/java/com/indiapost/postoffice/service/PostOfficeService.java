package com.indiapost.postoffice.service;

import com.indiapost.postoffice.dto.PostOfficeDto;
import com.indiapost.postoffice.dto.PostalApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

/**
 * Service layer for interacting with the external Postal PIN Code API.
 * <p>
 * Integrates with {@code api.postalpincode.in} to provide:
 * <ul>
 *   <li>PIN code-based post office lookup</li>
 *   <li>Post office branch name search</li>
 * </ul>
 * Responses are cached to reduce external API calls and improve
 * response times for repeated queries.
 * </p>
 */
@Slf4j
@Service
public class PostOfficeService {

    private final WebClient webClient;

    /**
     * Constructor — initializes the WebClient with the external API base URL.
     *
     * @param baseUrl base URL for the Postal PIN Code API
     */
    public PostOfficeService(@Value("${postoffice.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Search post offices by 6-digit PIN code.
     * <p>
     * Results are cached under the "pincode" cache region to avoid
     * redundant external API calls for the same PIN code.
     * </p>
     *
     * @param pincode 6-digit Indian postal PIN code
     * @return list of matching {@link PostOfficeDto} objects
     */
    @Cacheable(value = "pincode", key = "#pincode")
    public List<PostOfficeDto> searchByPincode(String pincode) {
        log.info("Fetching post offices for pincode: {}", pincode);

        try {
            List<PostalApiResponse> responses = webClient.get()
                    .uri("/pincode/{pincode}", pincode)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PostalApiResponse>>() {})
                    .block();

            if (responses != null && !responses.isEmpty()) {
                PostalApiResponse response = responses.get(0);

                if ("Success".equals(response.getStatus()) && response.getPostOffice() != null) {
                    // Enrich each post office with the queried pincode
                    response.getPostOffice().forEach(po -> po.setPincode(pincode));
                    log.info("Found {} post office(s) for pincode {}", response.getPostOffice().size(), pincode);
                    return response.getPostOffice();
                }

                log.warn("No post offices found for pincode {}: {}", pincode, response.getMessage());
            }
        } catch (Exception e) {
            log.error("Error fetching post offices for pincode {}: {}", pincode, e.getMessage());
        }

        return Collections.emptyList();
    }

    /**
     * Search post offices by branch name (partial match supported).
     * <p>
     * Results are cached under the "postoffice" cache region.
     * </p>
     *
     * @param name post office branch name to search
     * @return list of matching {@link PostOfficeDto} objects
     */
    @Cacheable(value = "postoffice", key = "#name")
    public List<PostOfficeDto> searchByName(String name) {
        log.info("Searching post offices by name: {}", name);

        try {
            List<PostalApiResponse> responses = webClient.get()
                    .uri("/postoffice/{name}", name)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PostalApiResponse>>() {})
                    .block();

            if (responses != null && !responses.isEmpty()) {
                PostalApiResponse response = responses.get(0);

                if ("Success".equals(response.getStatus()) && response.getPostOffice() != null) {
                    log.info("Found {} post office(s) matching '{}'", response.getPostOffice().size(), name);
                    return response.getPostOffice();
                }

                log.warn("No post offices found for name '{}': {}", name, response.getMessage());
            }
        } catch (Exception e) {
            log.error("Error searching post offices by name '{}': {}", name, e.getMessage());
        }

        return Collections.emptyList();
    }

    /**
     * Returns the list of all Indian states and union territories.
     * <p>
     * This is a static list used for frontend filtering dropdowns.
     * </p>
     *
     * @return list of state/UT names sorted alphabetically
     */
    public List<String> getAllStates() {
        return List.of(
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
                "Chhattisgarh", "Goa", "Gujarat", "Haryana",
                "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala",
                "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya",
                "Mizoram", "Nagaland", "Odisha", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
                "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
                "Andaman and Nicobar Islands", "Chandigarh",
                "Dadra and Nagar Haveli and Daman and Diu", "Delhi",
                "Jammu and Kashmir", "Ladakh", "Lakshadweep", "Puducherry"
        );
    }
}
