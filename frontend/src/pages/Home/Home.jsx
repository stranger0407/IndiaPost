import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Home.css';

/**
 * Home Page Component
 * 
 * Landing page for the India Post Portal featuring:
 * - Hero section with call-to-action buttons
 * - Key statistics about India Post
 * - Feature highlights (PIN search, tracking, services)
 * - Quick PIN code search bar
 * 
 * @component
 */
export default function Home() {
  const navigate = useNavigate();
  const [quickPincode, setQuickPincode] = useState('');

  /**
   * Handle quick search form submission.
   * Navigates to the search page with the entered PIN code.
   */
  const handleQuickSearch = (e) => {
    e.preventDefault();
    if (quickPincode.trim()) {
      navigate(`/search?pincode=${quickPincode.trim()}`);
    }
  };

  return (
    <main id="home-page">
      {/* ====== Hero Section ====== */}
      <section className="hero" id="hero-section">
        <div className="container">
          <span className="hero-badge">🇮🇳 India's Largest Postal Network</span>

          <h1>
            Explore <span className="gradient-text">India Post</span><br />
            At Your Fingertips
          </h1>

          <p className="hero-description">
            Search from over 1,64,000+ post offices across India, track your
            consignments in real-time, and discover the full range of postal
            services — all powered by official open-source government APIs.
          </p>

          <div className="hero-actions">
            <Link to="/search" className="btn btn-primary btn-lg" id="hero-search-btn">
              🔍 Find Post Office
            </Link>
            <Link to="/track" className="btn btn-secondary btn-lg" id="hero-track-btn">
              📦 Track Consignment
            </Link>
          </div>
        </div>
      </section>

      {/* ====== Stats Section ====== */}
      <section className="stats-section" id="stats-section">
        <div className="container">
          <div className="stats-grid">
            {[
              { icon: '🏤', value: '1,64,000+', label: 'Post Offices Nationwide' },
              { icon: '🗺️', value: '23', label: 'Postal Circles' },
              { icon: '📬', value: '6,00,000+', label: 'PIN Codes Served' },
              { icon: '👥', value: '5,00,000+', label: 'Dedicated Employees' },
            ].map(({ icon, value, label }, i) => (
              <div className="stat-card glass-card" key={label}>
                <div className="stat-icon">{icon}</div>
                <div className="stat-value">{value}</div>
                <div className="stat-label">{label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ====== Features Section ====== */}
      <section className="features-section" id="features-section">
        <div className="container">
          <div className="section-header">
            <h2>What You Can Do</h2>
            <p>
              Powerful tools to explore India's postal infrastructure,
              powered by official government data sources.
            </p>
          </div>

          <div className="features-grid">
            {[
              {
                icon: '📍',
                title: 'PIN Code Lookup',
                description: 'Search any 6-digit PIN code to find all post offices in that area with complete details including branch type, district, and delivery status.',
              },
              {
                icon: '🔎',
                title: 'Post Office Search',
                description: 'Find post offices by name across all of India. Get detailed information about branch types, circles, regions, and delivery capabilities.',
              },
              {
                icon: '📦',
                title: 'Consignment Tracking',
                description: 'Track your Speed Post, Registered Post, Parcel, and EMS consignments. Get status updates and direct links to the official tracking portal.',
              },
              {
                icon: '🏛️',
                title: 'Service Directory',
                description: 'Explore the complete range of India Post services — from traditional mail to banking, insurance, and e-commerce logistics solutions.',
              },
              {
                icon: '📊',
                title: 'Open Data Powered',
                description: 'Built on official open-source government APIs from data.gov.in and postalpincode.in, ensuring accurate and up-to-date information.',
              },
              {
                icon: '⚡',
                title: 'Fast & Reliable',
                description: 'Microservices architecture with intelligent caching delivers lightning-fast responses. Search results are cached for instant repeat queries.',
              },
            ].map(({ icon, title, description }, i) => (
              <div className={`feature-card glass-card stagger-${i + 1}`} key={title}>
                <div className="feature-icon">{icon}</div>
                <h3>{title}</h3>
                <p>{description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ====== Quick Search Section ====== */}
      <section className="quick-search" id="quick-search-section">
        <div className="container">
          <div className="quick-search-card glass-card">
            <h2>Quick PIN Code Lookup</h2>
            <p>Enter a 6-digit PIN code to instantly find all post offices in that area.</p>

            <form className="search-form" onSubmit={handleQuickSearch} id="quick-search-form">
              <input
                type="text"
                className="input"
                id="quick-pincode-input"
                placeholder="Enter PIN code (e.g., 110001)"
                value={quickPincode}
                onChange={(e) => setQuickPincode(e.target.value)}
                maxLength={6}
                pattern="[0-9]{6}"
              />
              <button type="submit" className="btn btn-primary" id="quick-search-btn">
                Search →
              </button>
            </form>
          </div>
        </div>
      </section>
    </main>
  );
}
