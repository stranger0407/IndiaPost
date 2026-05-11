import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { searchByPincode, searchByName } from '../../services/api';
import './Search.css';

/**
 * Search Page Component
 * 
 * Allows users to search for post offices by:
 * - 6-digit PIN code
 * - Post office branch name
 * 
 * Results are displayed as cards with detailed information
 * including branch type, district, state, and delivery status.
 * 
 * @component
 */
export default function Search() {
  const [searchParams] = useSearchParams();
  const [searchMode, setSearchMode] = useState('pincode');
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searched, setSearched] = useState(false);

  // Handle pre-filled PIN code from URL query params
  useEffect(() => {
    const pincode = searchParams.get('pincode');
    if (pincode) {
      setSearchMode('pincode');
      setQuery(pincode);
      performSearch('pincode', pincode);
    }
  }, [searchParams]);

  /**
   * Perform the search via the backend API.
   * 
   * @param {string} mode - 'pincode' or 'name'
   * @param {string} searchQuery - the search query string
   */
  async function performSearch(mode, searchQuery) {
    if (!searchQuery.trim()) return;

    setLoading(true);
    setError('');
    setResults([]);
    setSearched(true);

    try {
      let response;
      if (mode === 'pincode') {
        response = await searchByPincode(searchQuery.trim());
      } else {
        response = await searchByName(searchQuery.trim());
      }

      if (response.success && response.data) {
        setResults(response.data);
      } else {
        setError(response.message || 'No results found.');
      }
    } catch (err) {
      setError(err.message || 'Failed to search. Please try again.');
    } finally {
      setLoading(false);
    }
  }

  /**
   * Handle search form submission.
   */
  const handleSearch = (e) => {
    e.preventDefault();
    performSearch(searchMode, query);
  };

  /**
   * Get the appropriate badge class based on branch type.
   * 
   * @param {string} type - Branch type (e.g., "Head Post Office")
   * @returns {string} CSS badge class name
   */
  function getBranchBadge(type) {
    if (type?.includes('Head')) return 'badge-error';
    if (type?.includes('Sub')) return 'badge-info';
    return 'badge-warning';
  }

  return (
    <main className="search-page" id="search-page">
      <div className="container">
        {/* Header */}
        <div className="section-header">
          <h1>Find Post Office</h1>
          <p>Search from over 1,64,000+ post offices across India by PIN code or branch name.</p>
        </div>

        {/* Search Mode Tabs */}
        <div className="search-tabs" id="search-tabs">
          <button
            className={`search-tab ${searchMode === 'pincode' ? 'active' : ''}`}
            onClick={() => { setSearchMode('pincode'); setQuery(''); setResults([]); setSearched(false); }}
            id="tab-pincode"
          >
            📍 By PIN Code
          </button>
          <button
            className={`search-tab ${searchMode === 'name' ? 'active' : ''}`}
            onClick={() => { setSearchMode('name'); setQuery(''); setResults([]); setSearched(false); }}
            id="tab-name"
          >
            🔎 By Name
          </button>
        </div>

        {/* Search Form */}
        <form onSubmit={handleSearch} id="search-form">
          <div className="search-input-group">
            <input
              type="text"
              className="input"
              id="search-input"
              placeholder={
                searchMode === 'pincode'
                  ? 'Enter 6-digit PIN code (e.g., 110001)'
                  : 'Enter post office name (e.g., New Delhi)'
              }
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              maxLength={searchMode === 'pincode' ? 6 : 100}
            />
            <button type="submit" className="btn btn-primary" id="search-submit-btn" disabled={loading}>
              {loading ? 'Searching...' : 'Search'}
            </button>
          </div>
        </form>

        {/* Loading State */}
        {loading && (
          <div className="loading-container" id="search-loading">
            <div className="loading-spinner" />
            <p className="loading-text">Searching post offices...</p>
          </div>
        )}

        {/* Error State */}
        {error && !loading && (
          <div className="empty-state" id="search-error">
            <div className="empty-state-icon">🔍</div>
            <h3>No Results Found</h3>
            <p>{error}</p>
          </div>
        )}

        {/* Results */}
        {results.length > 0 && !loading && (
          <>
            <div className="results-header" id="results-header">
              <div className="results-count">
                Found <strong>{results.length}</strong> post office{results.length !== 1 ? 's' : ''}
              </div>
            </div>

            <div className="results-grid" id="results-grid">
              {results.map((po, index) => (
                <div
                  className={`po-card glass-card stagger-${Math.min(index + 1, 5)}`}
                  key={`${po.name}-${index}`}
                  id={`po-card-${index}`}
                >
                  <div className="po-card-header">
                    <span className="po-card-name">{po.name || po.Name}</span>
                    <span className={`badge po-card-type ${getBranchBadge(po.branchType || po.BranchType)}`}>
                      {po.branchType || po.BranchType}
                    </span>
                  </div>

                  <div className="po-card-details">
                    {(po.pincode || po.Pincode) && (
                      <div className="po-detail">
                        <span className="po-detail-label">PIN Code</span>
                        <span className="po-detail-value">{po.pincode || po.Pincode}</span>
                      </div>
                    )}
                    <div className="po-detail">
                      <span className="po-detail-label">District</span>
                      <span className="po-detail-value">{po.district || po.District}</span>
                    </div>
                    <div className="po-detail">
                      <span className="po-detail-label">State</span>
                      <span className="po-detail-value">{po.state || po.State}</span>
                    </div>
                    <div className="po-detail">
                      <span className="po-detail-label">Division</span>
                      <span className="po-detail-value">{po.division || po.Division}</span>
                    </div>
                    <div className="po-detail">
                      <span className="po-detail-label">Region</span>
                      <span className="po-detail-value">{po.region || po.Region}</span>
                    </div>
                    <div className="po-detail">
                      <span className="po-detail-label">Delivery</span>
                      <span className="po-detail-value">
                        {(po.deliveryStatus || po.DeliveryStatus) === 'Delivery' ? '✅ Yes' : '❌ No'}
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </>
        )}

        {/* Initial Empty State */}
        {!searched && !loading && results.length === 0 && (
          <div className="empty-state" id="search-initial">
            <div className="empty-state-icon">📮</div>
            <h3>Search Post Offices</h3>
            <p>Enter a PIN code or post office name above to get started.</p>
          </div>
        )}
      </div>
    </main>
  );
}
