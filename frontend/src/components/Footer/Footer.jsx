import { Link } from 'react-router-dom';
import './Footer.css';

/**
 * Footer Component
 * 
 * Site-wide footer with brand info, quick links, service links,
 * and official India Post references.
 * 
 * @component
 */
export default function Footer() {
  return (
    <footer className="footer" id="site-footer">
      <div className="container">
        <div className="footer-grid">
          {/* Brand Section */}
          <div className="footer-brand">
            <h3>📮 India Post Portal</h3>
            <p>
              India Post, with over 1,64,000+ post offices, is the largest postal
              network in the world. This portal provides easy access to post office
              information, PIN code lookup, and consignment tracking services.
            </p>
          </div>

          {/* Quick Links */}
          <div className="footer-column">
            <h4>Quick Links</h4>
            <ul>
              <li><Link to="/">Home</Link></li>
              <li><Link to="/search">Find Post Office</Link></li>
              <li><Link to="/track">Track Consignment</Link></li>
              <li><Link to="/services">Services</Link></li>
              <li><Link to="/about">About India Post</Link></li>
            </ul>
          </div>

          {/* Services */}
          <div className="footer-column">
            <h4>Services</h4>
            <ul>
              <li><a href="https://www.indiapost.gov.in" target="_blank" rel="noopener noreferrer">Speed Post</a></li>
              <li><a href="https://www.indiapost.gov.in" target="_blank" rel="noopener noreferrer">Registered Post</a></li>
              <li><a href="https://www.indiapost.gov.in" target="_blank" rel="noopener noreferrer">Money Order</a></li>
              <li><a href="https://www.ippbonline.com" target="_blank" rel="noopener noreferrer">Postal Banking</a></li>
              <li><a href="https://www.indiapost.gov.in" target="_blank" rel="noopener noreferrer">Philately</a></li>
            </ul>
          </div>

          {/* Official Links */}
          <div className="footer-column">
            <h4>Official</h4>
            <ul>
              <li><a href="https://www.indiapost.gov.in" target="_blank" rel="noopener noreferrer">India Post Official</a></li>
              <li><a href="https://www.ippbonline.com" target="_blank" rel="noopener noreferrer">IPPB Banking</a></li>
              <li><a href="https://data.gov.in" target="_blank" rel="noopener noreferrer">data.gov.in</a></li>
              <li><a href="https://api.postalpincode.in" target="_blank" rel="noopener noreferrer">Postal API</a></li>
            </ul>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="footer-bottom">
          <p>&copy; {new Date().getFullYear()} India Post Portal. Built with ❤️ for Digital India.</p>
          <p>
            Powered by open-source APIs from{' '}
            <a href="https://api.postalpincode.in" target="_blank" rel="noopener noreferrer">
              postalpincode.in
            </a>
          </p>
        </div>
      </div>
    </footer>
  );
}
