import './About.css';

/**
 * About Page Component
 * 
 * Comprehensive information about India Post including:
 * - Overview and mission
 * - Historical timeline
 * - Key facts and statistics
 * - API documentation references
 * 
 * All information sourced from official India Post data and
 * public government records.
 * 
 * @component
 */
export default function About() {
  return (
    <main className="about-page" id="about-page">
      <div className="container">
        {/* Hero */}
        <div className="about-hero">
          <h1>
            About <span className="gradient-text">India Post</span>
          </h1>
          <p>
            The Department of Posts, trading as India Post, is a government-operated
            postal system under the Ministry of Communications, Government of India.
            With over 1,64,000 post offices, it is the <strong>largest postal network
            in the world</strong>, serving every corner of the nation.
          </p>
        </div>

        {/* Overview Section */}
        <section className="about-section" id="about-overview">
          <h2>🏛️ Overview</h2>
          <div className="about-content glass-card">
            <p>
              India Post is the backbone of communication and financial inclusion in
              India. Established over 170 years ago, the postal service has evolved from
              simple letter delivery to a comprehensive service provider offering banking,
              insurance, e-commerce logistics, and government service delivery.
            </p>
            <p>
              The country is divided into <strong>23 postal circles</strong>, each headed
              by a Chief Postmaster General. These circles are further divided into regions,
              divisions, and individual post offices. About 90% of post offices are in rural
              areas, making India Post the most extensive rural infrastructure in the country.
            </p>
            <p>
              India Post plays a crucial role in financial inclusion through the India Post
              Payments Bank (IPPB), small savings schemes, Postal Life Insurance, and
              direct benefit transfers for government welfare schemes.
            </p>
          </div>
        </section>

        {/* History Timeline */}
        <section className="about-section" id="about-history">
          <h2>📜 Historical Journey</h2>
          <div className="history-timeline">
            {[
              {
                year: '1727',
                title: 'First Post Office Established',
                desc: 'The East India Company opened the first post office in Calcutta (now Kolkata), marking the formal beginning of postal services in India.',
              },
              {
                year: '1774',
                title: 'Warren Hastings Reforms',
                desc: 'Governor General Warren Hastings established the General Post Office in Calcutta, opening postal services to the general public for the first time.',
              },
              {
                year: '1854',
                title: 'Modern Postal System Born',
                desc: 'Lord Dalhousie introduced uniform postage rates across India with the India Post Office Act of 1854. This year also saw the introduction of postage stamps and the appointment of the first Director General of Post Offices.',
              },
              {
                year: '1911',
                title: 'First Airmail Flight',
                desc: 'The world\'s first official airmail flight carried mail from Allahabad to Naini, covering a distance of about 10 km — a historic milestone in global postal history.',
              },
              {
                year: '1947',
                title: 'Independence',
                desc: 'At the time of India\'s independence, the postal network had 23,344 post offices. The free nation embarked on an ambitious expansion to connect every village.',
              },
              {
                year: '1972',
                title: 'PIN Code System Introduced',
                desc: 'The 6-digit Postal Index Number (PIN) system was introduced on August 15, 1972, to simplify mail sorting and improve delivery efficiency across the vast network.',
              },
              {
                year: '1986',
                title: 'Speed Post Launched',
                desc: 'India Post introduced Speed Post, its premium express delivery service with guaranteed time-bound delivery and real-time tracking capabilities.',
              },
              {
                year: '2017',
                title: 'India Post Payments Bank',
                desc: 'IPPB was launched to provide affordable banking services to every household in India, leveraging the postal network for doorstep digital banking.',
              },
            ].map(({ year, title, desc }, i) => (
              <div className={`history-item stagger-${Math.min(i + 1, 5)}`} key={year}>
                <div className="history-dot" />
                <div className="history-year">{year}</div>
                <div className="history-title">{title}</div>
                <div className="history-desc">{desc}</div>
              </div>
            ))}
          </div>
        </section>

        {/* Key Facts */}
        <section className="about-section" id="about-facts">
          <h2>📊 Key Facts</h2>
          <div className="facts-grid">
            {[
              { icon: '🏤', title: '1,64,000+ Post Offices', desc: 'The largest postal network in the world, with ~90% offices in rural areas' },
              { icon: '🗺️', title: '23 Postal Circles', desc: 'Administrative divisions covering all states and union territories' },
              { icon: '📬', title: '6-Digit PIN System', desc: 'Introduced in 1972 — first digit represents the zone, first 3 digits the sorting district' },
              { icon: '🛫', title: 'World\'s First Airmail', desc: 'India hosted the world\'s first official airmail flight in 1911 at Allahabad' },
              { icon: '🏦', title: 'IPPB Banking', desc: 'India Post Payments Bank provides doorstep banking to every household' },
              { icon: '🛡️', title: 'PLI & RPLI', desc: 'Postal Life Insurance (since 1884) — one of the oldest insurance schemes in the world' },
            ].map(({ icon, title, desc }) => (
              <div className="fact-card glass-card" key={title}>
                <div className="fact-icon">{icon}</div>
                <h4>{title}</h4>
                <p>{desc}</p>
              </div>
            ))}
          </div>
        </section>

        {/* API Information */}
        <section className="about-section" id="about-api">
          <h2>🔗 Open Data & APIs</h2>
          <div className="api-info glass-card">
            <p style={{ color: 'var(--color-text-secondary)', marginBottom: 'var(--space-4)', lineHeight: 1.7 }}>
              This portal is powered by the <strong>Postal PIN Code API</strong> — a free,
              open-source REST API that provides access to India Post office data. No
              authentication required.
            </p>

            <div className="api-endpoints">
              <div className="api-endpoint">
                <span className="api-method">GET</span>
                <span className="api-path">https://api.postalpincode.in/pincode/&#123;PINCODE&#125;</span>
              </div>
              <div className="api-endpoint">
                <span className="api-method">GET</span>
                <span className="api-path">https://api.postalpincode.in/postoffice/&#123;NAME&#125;</span>
              </div>
            </div>

            <p style={{ color: 'var(--color-text-muted)', marginTop: 'var(--space-5)', fontSize: 'var(--font-size-xs)' }}>
              Source:{' '}
              <a href="https://api.postalpincode.in" target="_blank" rel="noopener noreferrer">
                api.postalpincode.in
              </a>
              {' '}|{' '}
              <a href="https://data.gov.in" target="_blank" rel="noopener noreferrer">
                data.gov.in
              </a>
            </p>
          </div>
        </section>
      </div>
    </main>
  );
}
