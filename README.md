<div align="center">
  <h1>📮 India Post Portal</h1>
  <p><strong>A modern, microservices-based portal for India Post services</strong></p>
  <p>
    Search post offices • Track consignments • Explore postal services
    <br/>
    Powered by official open-source Government of India APIs
  </p>

  <br/>

  ![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-6DB33F?style=for-the-badge&logo=spring-boot)
  ![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)
  ![Vite](https://img.shields.io/badge/Vite-8-646CFF?style=for-the-badge&logo=vite&logoColor=white)
  ![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven)
  ![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
</div>

---

## 🏗️ Architecture

This project follows a **microservices architecture** with a React frontend and Spring Boot backend services:

```
┌─────────────────────────────────────────────────────┐
│              React Frontend (Vite)                   │
│        Port 5173 — SPA with React Router            │
└──────────────────────┬──────────────────────────────┘
                       │ REST API calls
┌──────────────────────▼──────────────────────────────┐
│              API Gateway Service                     │
│           Spring Boot — Port 8080                    │
│     CORS • Routing • Request Proxying                │
└──────┬───────────────────────────────┬──────────────┘
       │                               │
┌──────▼──────────┐      ┌─────────────▼──────────────┐
│ PostOffice Svc  │      │   Tracking Service          │
│  Port 8081      │      │    Port 8082                │
│                 │      │                             │
│ • PIN lookup    │      │ • Consignment tracking      │
│ • Branch search │      │ • Service type ID           │
│ • State listing │      │ • Official portal link      │
│                 │      │ • Status timeline           │
│ External API:   │      │                             │
│ postalpincode.in│      │ Demo + Official redirect    │
└─────────────────┘      └────────────────────────────┘
```

## ✨ Features

### 🔍 Post Office Search
- **PIN Code Lookup** — Search by any 6-digit Indian PIN code to find all post offices in that area
- **Branch Name Search** — Find post offices by name across all of India
- **Rich Details** — Branch type, district, state, division, region, and delivery status
- **Response Caching** — Frequently queried PIN codes are cached for instant results

### 📦 Consignment Tracking
- **Tracking Number Validation** — Validates 13-character alphanumeric format (2 letters + 9 digits + 2 letters)
- **Service Type Detection** — Automatically identifies Speed Post, Registered Post, EMS, Parcel Post, etc.
- **Visual Timeline** — Chronological tracking history with status updates
- **Official Portal Link** — Direct link to India Post's official tracking portal for real-time data

### 🏛️ Service Directory
- **Complete Service Catalog** — Speed Post, Registered Post, EMS, Parcel Post, Book Post, COD
- **Financial Services** — IPPB Banking, Money Orders, Small Savings Schemes
- **Insurance** — PLI and RPLI information
- **Government Services** — Aadhaar, Passport, Pension, MGNREGA

### 📊 About India Post
- **Historical Timeline** — From 1727 to the modern era
- **Key Statistics** — 1,64,000+ post offices, 23 postal circles, 6,00,000+ PIN codes
- **API Documentation** — Open-source API endpoints and response formats

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Frontend** | React 18, Vite 8 | Single-page application |
| **Styling** | CSS Custom Properties | Design system with dark theme |
| **Routing** | React Router v7 | Client-side navigation |
| **API Gateway** | Spring Boot 3.4.5 | Request routing, CORS |
| **Microservices** | Spring Boot 3.4.5 | PostOffice + Tracking |
| **HTTP Client** | WebClient (WebFlux) | Non-blocking API calls |
| **Caching** | Spring Cache | In-memory response caching |
| **API Docs** | SpringDoc OpenAPI | Swagger UI |
| **Build** | Maven Multi-Module | Unified build system |
| **Java** | JDK 21 LTS | Latest LTS runtime |

## 📂 Project Structure

```
IndiaPost/
├── frontend/                           # React + Vite frontend
│   ├── src/
│   │   ├── components/
│   │   │   ├── Navbar/                 # Navigation bar
│   │   │   └── Footer/                # Site footer
│   │   ├── pages/
│   │   │   ├── Home/                  # Landing page
│   │   │   ├── Search/                # Post office search
│   │   │   ├── Track/                 # Consignment tracking
│   │   │   ├── Services/             # Service directory
│   │   │   └── About/                # About India Post
│   │   ├── services/
│   │   │   └── api.js                 # Centralized API layer
│   │   ├── index.css                  # Global design system
│   │   ├── App.jsx                    # Root component + routing
│   │   └── main.jsx                   # Entry point
│   └── package.json
│
├── backend/                            # Spring Boot microservices
│   ├── pom.xml                        # Parent POM (multi-module)
│   │
│   ├── api-gateway/                   # API Gateway (port 8080)
│   │   └── src/main/java/com/indiapost/gateway/
│   │       ├── ApiGatewayApplication.java
│   │       ├── config/
│   │       │   ├── CorsConfig.java
│   │       │   └── WebClientConfig.java
│   │       └── controller/
│   │           ├── PostOfficeGatewayController.java
│   │           ├── TrackingGatewayController.java
│   │           └── HealthController.java
│   │
│   ├── postoffice-service/            # Post Office Service (port 8081)
│   │   └── src/main/java/com/indiapost/postoffice/
│   │       ├── PostOfficeServiceApplication.java
│   │       ├── controller/PostOfficeController.java
│   │       ├── service/PostOfficeService.java
│   │       ├── dto/
│   │       │   ├── PostOfficeDto.java
│   │       │   ├── PostalApiResponse.java
│   │       │   └── ApiResponse.java
│   │       └── exception/GlobalExceptionHandler.java
│   │
│   └── tracking-service/             # Tracking Service (port 8082)
│       └── src/main/java/com/indiapost/tracking/
│           ├── TrackingServiceApplication.java
│           ├── controller/TrackingController.java
│           ├── service/TrackingService.java
│           ├── dto/
│           │   ├── TrackingResultDto.java
│           │   ├── TrackingEventDto.java
│           │   ├── ServiceTypeDto.java
│           │   └── ApiResponse.java
│           └── exception/GlobalExceptionHandler.java
│
├── .gitignore
└── README.md
```

## 🚀 Quick Start

### Prerequisites

- **Java 21** (JDK) — [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- **Node.js 18+** — [Download](https://nodejs.org/)

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/IndiaPost.git
cd IndiaPost
```

### 2. Start Backend Services

Open **three separate terminals** and run each service:

```bash
# Terminal 1 — Post Office Service (port 8081)
cd backend
mvn spring-boot:run -pl postoffice-service

# Terminal 2 — Tracking Service (port 8082)
cd backend
mvn spring-boot:run -pl tracking-service

# Terminal 3 — API Gateway (port 8080)
cd backend
mvn spring-boot:run -pl api-gateway
```

Or build and run all services at once:

```bash
cd backend
mvn clean package -DskipTests
java -jar postoffice-service/target/postoffice-service-1.0.0.jar &
java -jar tracking-service/target/tracking-service-1.0.0.jar &
java -jar api-gateway/target/api-gateway-1.0.0.jar &
```

### 3. Start Frontend

```bash
cd frontend
npm install
npm run dev
```

### 4. Open in Browser

Visit **http://localhost:5173** to use the portal.

## 📡 API Endpoints

### API Gateway (Port 8080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/health` | Gateway health check |
| `GET` | `/api/postoffice/pincode/{pincode}` | Search by PIN code |
| `GET` | `/api/postoffice/search?name={name}` | Search by branch name |
| `GET` | `/api/postoffice/states` | List all states/UTs |
| `GET` | `/api/tracking/{trackingNumber}` | Track consignment |
| `GET` | `/api/tracking/services` | List service types |

### Swagger UI (API Documentation)

- Post Office Service: http://localhost:8081/swagger-ui.html
- Tracking Service: http://localhost:8082/swagger-ui.html

## 🔌 External APIs Used

### Postal PIN Code API (Free, No Auth Required)

This portal integrates with the open-source **[Postal PIN Code API](https://api.postalpincode.in)** maintained by the community using Government of India data:

| Endpoint | Description |
|----------|-------------|
| `GET https://api.postalpincode.in/pincode/{PINCODE}` | Get post offices by PIN code |
| `GET https://api.postalpincode.in/postoffice/{NAME}` | Get post offices by name |

**Note on Tracking:** India Post does not provide a public tracking API. The tracking feature in this portal provides:
- Tracking number format validation
- Service type identification from prefix
- Demo tracking data for testing
- Direct link to the [official India Post tracking portal](https://www.indiapost.gov.in/VAS/Pages/trackconsignment.aspx)

## 🏤 About India Post

**India Post** (Department of Posts), under the Ministry of Communications, Government of India, is the **world's largest postal network** with over **1,64,000 post offices**. Key facts:

- 🗓️ **Founded:** 1854 (modern form under Lord Dalhousie)
- 🏤 **Post Offices:** 1,64,000+ (90% in rural areas)
- 🗺️ **Postal Circles:** 23 administrative divisions
- 📬 **PIN Code System:** Introduced August 15, 1972
- 🛫 **Historic First:** World's first official airmail flight (Allahabad, 1911)
- 🏦 **IPPB:** India Post Payments Bank for doorstep digital banking
- 🛡️ **Insurance:** PLI (since 1884) — one of the world's oldest schemes

### Services Offered
- **Mail:** Speed Post, Registered Post, Parcel Post, Book Post, EMS
- **Financial:** Savings schemes, IPPB Banking, Money Orders, ATMs
- **Insurance:** Postal Life Insurance (PLI), Rural PLI (RPLI)
- **Government:** Aadhaar enrollment, Passport services, Pension disbursement

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### API Health Checks
```bash
# Check all services
curl http://localhost:8080/api/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health

# Test PIN code search
curl http://localhost:8080/api/postoffice/pincode/110001

# Test tracking
curl http://localhost:8080/api/tracking/EE123456789IN
```

## 📝 Code Quality Standards

This project follows industry best practices:

- ✅ **Comprehensive JavaDoc** — All public classes and methods documented
- ✅ **JSDoc Comments** — All React components and API functions documented
- ✅ **Separation of Concerns** — Controller → Service → External API layers
- ✅ **DTO Pattern** — Clean data transfer between layers
- ✅ **Global Exception Handling** — Standardized error responses
- ✅ **Input Validation** — PIN code format, tracking number format
- ✅ **Response Caching** — Reduce external API calls
- ✅ **CORS Configuration** — Secure cross-origin setup
- ✅ **OpenAPI/Swagger** — Auto-generated API documentation
- ✅ **Actuator Health Checks** — Production-ready monitoring
- ✅ **Lombok** — Reduced boilerplate with compile-time code generation
- ✅ **CSS Custom Properties** — Themeable, maintainable design system
- ✅ **Responsive Design** — Mobile-first, works on all devices

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- [India Post](https://www.indiapost.gov.in) — For the world's largest postal network
- [Postal PIN Code API](https://api.postalpincode.in) — Free, open-source postal data API
- [data.gov.in](https://data.gov.in) — Government of India open data platform
- [Spring Boot](https://spring.io/projects/spring-boot) — Enterprise Java framework
- [React](https://react.dev) — UI library by Meta
- [Vite](https://vite.dev) — Next-generation frontend build tool

---

<div align="center">
  <p>Built with ❤️ for <strong>Digital India</strong></p>
  <p>🇮🇳 Jai Hind 🇮🇳</p>
</div>
