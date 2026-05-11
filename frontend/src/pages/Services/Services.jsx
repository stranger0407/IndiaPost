import './Services.css';

/**
 * Services Page Component
 * 
 * Showcases all India Post services organized into:
 * - Postal service types with tracking prefixes
 * - Service categories (mail, financial, insurance, etc.)
 * 
 * @component
 */
export default function Services() {
  /** Postal service types with descriptions and delivery estimates */
  const postalServices = [
    {
      icon: '⚡',
      name: 'Speed Post',
      prefix: 'EE',
      description: 'Express delivery service with guaranteed time-bound delivery across India and abroad. Features real-time tracking, insurance coverage, and proof of delivery.',
      delivery: '1-3 days (metro), 2-5 days (non-metro)',
    },
    {
      icon: '📝',
      name: 'Registered Post',
      prefix: 'RR',
      description: 'Secure postal service providing proof of posting and delivery. Ideal for important documents, legal notices, and valuable correspondence.',
      delivery: '3-7 days',
    },
    {
      icon: '🌍',
      name: 'EMS (International)',
      prefix: 'EM',
      description: 'International Express Mail Service for fast, reliable delivery to over 200+ countries. Includes full tracking and customs clearance support.',
      delivery: '5-10 days (international)',
    },
    {
      icon: '📦',
      name: 'Parcel Post',
      prefix: 'CP',
      description: 'Cost-effective service for sending parcels up to 35 kg within India. Available in both surface and air mail options.',
      delivery: '7-14 days',
    },
    {
      icon: '💰',
      name: 'Cash on Delivery',
      prefix: 'CD',
      description: 'Send articles with payment collected upon delivery. The collected amount is remitted to the sender via money order.',
      delivery: '5-10 days',
    },
    {
      icon: '📚',
      name: 'Book Post',
      prefix: 'RB',
      description: 'Economical option specifically designed for sending books, periodicals, and educational materials at reduced postal rates.',
      delivery: '7-14 days',
    },
  ];

  /** Service categories beyond mail delivery */
  const categories = [
    {
      icon: '🏦',
      name: 'Financial Services',
      description: 'India Post Payments Bank (IPPB), savings accounts, recurring deposits, and various small savings schemes.',
    },
    {
      icon: '💸',
      name: 'Money Transfer',
      description: 'Electronic Money Order (eMO), instant money transfers, and international money transfer services (IMTS).',
    },
    {
      icon: '🛡️',
      name: 'Insurance',
      description: 'Postal Life Insurance (PLI) and Rural Postal Life Insurance (RPLI) — affordable life coverage for all Indians.',
    },
    {
      icon: '🪪',
      name: 'Government Services',
      description: 'Aadhaar enrollment, passport services (POPSK), pension disbursement, and MGNREGA wage payments.',
    },
    {
      icon: '🏷️',
      name: 'Philately',
      description: 'Commemorative and special stamp collections, first-day covers, and mint stamp sets for collectors.',
    },
    {
      icon: '🛒',
      name: 'E-Commerce',
      description: 'ePost, electronic bill collection, direct post advertising, and logistics solutions for online businesses.',
    },
  ];

  return (
    <main className="services-page" id="services-page">
      <div className="container">
        {/* Header */}
        <div className="section-header">
          <h1>India Post Services</h1>
          <p>
            Explore the comprehensive range of postal, financial, and government
            services offered by India Post across 1,64,000+ post offices.
          </p>
        </div>

        {/* Postal Services */}
        <div className="services-grid" id="postal-services">
          {postalServices.map(({ icon, name, prefix, description, delivery }, i) => (
            <div className={`service-card glass-card stagger-${Math.min(i + 1, 5)}`} key={name} id={`service-${prefix}`}>
              <div className="service-card-header">
                <div className="service-icon">{icon}</div>
                <div>
                  <div className="service-card-title">{name}</div>
                  <div className="service-card-subtitle">Prefix: {prefix}XXXXXXXXX</div>
                </div>
              </div>

              <p>{description}</p>

              <div className="service-meta">
                <div className="service-delivery">
                  🕐 <span>{delivery}</span>
                </div>
                <span className="badge badge-info">{prefix}</span>
              </div>
            </div>
          ))}
        </div>

        {/* Service Categories */}
        <div className="categories-section">
          <div className="section-header">
            <h2>Beyond Mail Delivery</h2>
            <p>
              India Post is much more than a postal service — it's a lifeline
              connecting rural India to banking, insurance, and government services.
            </p>
          </div>

          <div className="categories-grid" id="service-categories">
            {categories.map(({ icon, name, description }) => (
              <div className="category-card glass-card" key={name}>
                <div className="category-icon">{icon}</div>
                <h3>{name}</h3>
                <p>{description}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
}
