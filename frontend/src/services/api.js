/**
 * API Service Layer
 * 
 * Centralized HTTP client for communicating with the Spring Boot
 * API Gateway. All API calls go through this module to ensure
 * consistent error handling, base URL management, and response parsing.
 * 
 * @module api
 */

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Generic fetch wrapper with error handling.
 * 
 * @param {string} endpoint - API endpoint path (appended to base URL)
 * @param {Object} [options] - Fetch options (method, headers, body, etc.)
 * @returns {Promise<Object>} Parsed JSON response
 * @throws {Error} If the response is not ok or network fails
 */
async function apiFetch(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;

  try {
    const response = await fetch(url, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || `HTTP ${response.status}: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('Unable to connect to the server. Please ensure the backend is running.');
    }
    throw error;
  }
}

// ================================================================
//  Post Office API
// ================================================================

/**
 * Search post offices by 6-digit PIN code.
 * 
 * @param {string} pincode - 6-digit Indian postal PIN code
 * @returns {Promise<Object>} API response with post office data
 */
export async function searchByPincode(pincode) {
  return apiFetch(`/postoffice/pincode/${pincode}`);
}

/**
 * Search post offices by branch name.
 * 
 * @param {string} name - Post office branch name (min 3 chars)
 * @returns {Promise<Object>} API response with matching post offices
 */
export async function searchByName(name) {
  return apiFetch(`/postoffice/search?name=${encodeURIComponent(name)}`);
}

/**
 * Get list of all Indian states and union territories.
 * 
 * @returns {Promise<Object>} API response with state names
 */
export async function getStates() {
  return apiFetch('/postoffice/states');
}

// ================================================================
//  Tracking API
// ================================================================

/**
 * Track a consignment by its tracking number.
 * 
 * @param {string} trackingNumber - 13-char alphanumeric tracking ID
 * @returns {Promise<Object>} API response with tracking details
 */
export async function trackConsignment(trackingNumber) {
  return apiFetch(`/tracking/${encodeURIComponent(trackingNumber)}`);
}

/**
 * Get all supported postal service types.
 * 
 * @returns {Promise<Object>} API response with service type list
 */
export async function getServiceTypes() {
  return apiFetch('/tracking/services');
}

// ================================================================
//  Health Check
// ================================================================

/**
 * Check API Gateway health status.
 * 
 * @returns {Promise<Object>} Health status object
 */
export async function checkHealth() {
  return apiFetch('/health');
}
