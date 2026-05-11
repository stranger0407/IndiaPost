import { useState } from 'react';
import { trackConsignment } from '../../services/api';
import './Track.css';

/**
 * Track Page Component
 * 
 * Allows users to track India Post consignments by entering
 * a 13-character tracking number. Displays:
 * - Tracking summary with service type and status
 * - Visual timeline of tracking events
 * - Direct link to official India Post tracking portal
 * 
 * @component
 */
export default function Track() {
  const [trackingNumber, setTrackingNumber] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  /**
   * Handle tracking form submission.
   * Validates format and calls the tracking API.
   */
  const handleTrack = async (e) => {
    e.preventDefault();
    const normalized = trackingNumber.toUpperCase().trim();

    if (!normalized) return;

    setLoading(true);
    setError('');
    setResult(null);

    try {
      const response = await trackConsignment(normalized);

      if (response.success && response.data) {
        setResult(response.data);
      } else {
        setError(response.message || 'Unable to track the consignment.');
      }
    } catch (err) {
      setError(err.message || 'Failed to track. Please check your tracking number and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="track-page" id="track-page">
      <div className="container">
        {/* Header */}
        <div className="section-header">
          <h1>Track Consignment</h1>
          <p>Enter your tracking number to check the status of your consignment.</p>
        </div>

        {/* Tracking Form */}
        <div className="track-form-card glass-card" id="track-form-card">
          <form onSubmit={handleTrack} className="track-form" id="track-form">
            <input
              type="text"
              className="input"
              id="tracking-input"
              placeholder="e.g., EE123456789IN"
              value={trackingNumber}
              onChange={(e) => setTrackingNumber(e.target.value.toUpperCase())}
              maxLength={13}
            />
            <button type="submit" className="btn btn-primary btn-lg" id="track-submit-btn" disabled={loading}>
              {loading ? 'Tracking...' : '📦 Track'}
            </button>
          </form>
          <p className="track-hint">
            Enter 13-character tracking number: 2 letters + 9 digits + 2 letters (e.g., EE123456789IN, RR987654321IN)
          </p>
        </div>

        {/* Loading */}
        {loading && (
          <div className="loading-container" id="track-loading">
            <div className="loading-spinner" />
            <p className="loading-text">Tracking your consignment...</p>
          </div>
        )}

        {/* Error */}
        {error && !loading && (
          <div className="empty-state" id="track-error">
            <div className="empty-state-icon">⚠️</div>
            <h3>Tracking Error</h3>
            <p>{error}</p>
          </div>
        )}

        {/* Tracking Result */}
        {result && !loading && (
          <div className="tracking-result" id="tracking-result">
            {/* Demo Notice */}
            <div className="demo-notice">
              <p>ℹ️ {result.note}</p>
            </div>

            {/* Tracking Header */}
            <div className="tracking-header-card glass-card">
              <div className="tracking-header-top">
                <span className="tracking-number-display">{result.trackingNumber}</span>
                <span className="tracking-status-badge badge badge-success">
                  {result.currentStatus}
                </span>
              </div>

              <div className="tracking-details-grid">
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Service Type</span>
                  <span className="tracking-detail-value">{result.serviceType}</span>
                </div>
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Origin</span>
                  <span className="tracking-detail-value">{result.origin}</span>
                </div>
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Destination</span>
                  <span className="tracking-detail-value">{result.destination}</span>
                </div>
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Booking Date</span>
                  <span className="tracking-detail-value">{result.bookingDate}</span>
                </div>
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Expected Delivery</span>
                  <span className="tracking-detail-value">{result.expectedDelivery}</span>
                </div>
                <div className="tracking-detail">
                  <span className="tracking-detail-label">Weight</span>
                  <span className="tracking-detail-value">{result.weight}</span>
                </div>
              </div>
            </div>

            {/* Timeline */}
            {result.events && result.events.length > 0 && (
              <div className="timeline-card glass-card" id="tracking-timeline">
                <h3 className="timeline-title">📋 Tracking History</h3>
                <div className="timeline">
                  {result.events.map((event, index) => (
                    <div
                      className={`timeline-event stagger-${Math.min(index + 1, 5)}`}
                      key={index}
                    >
                      <div className="timeline-dot" />
                      <div className="timeline-event-time">{event.timestamp}</div>
                      <div className="timeline-event-status">{event.status}</div>
                      <div className="timeline-event-location">📍 {event.location}</div>
                      <div className="timeline-event-desc">{event.description}</div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {/* Official Tracking Link */}
            <div className="official-link-card glass-card" id="official-tracking-link">
              <p>
                For real-time tracking with official India Post data,
                visit the official tracking portal:
              </p>
              <a
                href={result.officialTrackingUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="btn btn-primary"
              >
                🌐 Visit Official India Post Tracking →
              </a>
            </div>
          </div>
        )}

        {/* Initial Empty State */}
        {!result && !loading && !error && (
          <div className="empty-state" id="track-initial">
            <div className="empty-state-icon">📦</div>
            <h3>Track Your Shipment</h3>
            <p>Enter your tracking number above to see the delivery status and history.</p>
          </div>
        )}
      </div>
    </main>
  );
}
