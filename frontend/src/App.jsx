import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';
import Home from './pages/Home/Home';
import Search from './pages/Search/Search';
import Track from './pages/Track/Track';
import Services from './pages/Services/Services';
import About from './pages/About/About';

/**
 * App Component — Root of the India Post Portal frontend.
 * 
 * Sets up client-side routing with React Router and wraps
 * all pages with the shared Navbar and Footer layout.
 * 
 * Routes:
 * - /          → Home page (landing)
 * - /search    → Post office search (PIN code / name)
 * - /track     → Consignment tracking
 * - /services  → India Post services directory
 * - /about     → About India Post
 * 
 * @component
 */
export default function App() {
  return (
    <Router>
      <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/search" element={<Search />} />
          <Route path="/track" element={<Track />} />
          <Route path="/services" element={<Services />} />
          <Route path="/about" element={<About />} />
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}
