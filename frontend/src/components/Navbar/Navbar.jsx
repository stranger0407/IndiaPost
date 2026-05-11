import { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';

/**
 * Navbar Component
 * 
 * Fixed navigation bar with glassmorphism backdrop effect.
 * Highlights the active route and collapses into a hamburger
 * menu on mobile viewports.
 * 
 * @component
 */
export default function Navbar() {
  const location = useLocation();
  const [isScrolled, setIsScrolled] = useState(false);
  const [isMobileOpen, setIsMobileOpen] = useState(false);

  // Track scroll position for navbar shrink effect
  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 20);
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  // Close mobile menu on route change
  useEffect(() => {
    setIsMobileOpen(false);
  }, [location.pathname]);

  /**
   * Navigation links configuration.
   * Add new pages here to include them in the navbar.
   */
  const navLinks = [
    { path: '/', label: 'Home', id: 'nav-home' },
    { path: '/search', label: 'Find Post Office', id: 'nav-search' },
    { path: '/track', label: 'Track Consignment', id: 'nav-track' },
    { path: '/services', label: 'Services', id: 'nav-services' },
    { path: '/about', label: 'About', id: 'nav-about' },
  ];

  return (
    <nav className={`navbar ${isScrolled ? 'scrolled' : ''}`} id="main-navbar">
      <div className="navbar-inner">
        {/* Brand logo and title */}
        <Link to="/" className="navbar-brand" id="navbar-brand">
          <div className="navbar-logo">📮</div>
          <div>
            <div className="navbar-title">India Post Portal</div>
            <div className="navbar-subtitle">Government of India</div>
          </div>
        </Link>

        {/* Mobile hamburger toggle */}
        <button
          className="navbar-toggle"
          id="navbar-toggle"
          onClick={() => setIsMobileOpen(!isMobileOpen)}
          aria-label="Toggle navigation"
        >
          <span />
          <span />
          <span />
        </button>

        {/* Navigation links */}
        <ul className={`navbar-links ${isMobileOpen ? 'open' : ''}`} id="navbar-links">
          {navLinks.map(({ path, label, id }) => (
            <li key={path}>
              <Link
                to={path}
                className={`navbar-link ${location.pathname === path ? 'active' : ''}`}
                id={id}
              >
                {label}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </nav>
  );
}
