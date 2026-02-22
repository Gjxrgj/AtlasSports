# AtlasSports

**AtlasSports** is a multi-tenant SaaS platform for sports venue booking, management, and monetization. It enables sports facility owners to manage venues, schedules, payments, subscriptions, and analytics while providing clients with a seamless booking experience.

> Built as a production-grade, event-driven, real-time platform with enterprise-level architecture and observability.

---

##  Features

###  Authentication & Authorization
- JWT-based authentication with refresh tokens
- Role-based access control: **Admin**, **Tenant**, **Client**
- Secure tenant isolation across all resources

###  Booking & Scheduling System
- Calendar-based time slot selection
- Real-time availability tracking
- Double-booking prevention
- Transactional reservations and payments

###  Subscription & Billing
- Stripe integration with webhooks
- Automated renewals, expirations, and invoicing
- Subscription-based access control for tenants

###  Real-Time Updates
- Live synchronization of bookings and availability
- Implemented via **WebSockets / SSE**
- Immediate updates across tenant dashboards and client views

###  Event-Driven Architecture
- Microservices communicating via **Kafka / RabbitMQ**
- Asynchronous handling of:
  - Bookings
  - Payments
  - Notifications
  - Analytics

###  High-Performance Search
- Venue discovery by:
  - Sport
  - Location
  - Availability
- Powered by **PostgreSQL Full-Text Search / Elasticsearch**

###  Performance & Reliability
- Redis caching layer
- Rate limiting for abuse protection
- Background jobs for:
  - Reminders
  - Expirations
  - Notifications
  - Maintenance tasks

###  Analytics & Dashboards
- Tenant and admin dashboards
- Metrics include:
  - Bookings
  - Revenue
  - Growth
  - Operational performance
- Interactive charts and insights

###  Observability & Monitoring
- Full monitoring stack:
  - **Prometheus** – metrics
  - **Grafana** – visualization
  - **Loki** – logs
  - **OpenTelemetry** – tracing
- Centralized alerting and system health visibility

---

##  Architecture

- **Backend:** Java, Spring Boot, Microservices
- **Frontend:** React
- **Database:** PostgreSQL
- **Cache:** Redis
- **Messaging:** Kafka
- **Search:** PostgreSQL FTS
- **Payments:** Stripe
- **Observability:** Prometheus, Grafana, Loki, OpenTelemetry

---

##  System Design Highlights

- Fully multi-tenant with strict data isolation
- Event-driven workflows for scalability and fault tolerance
- Real-time UX with consistent state across clients
- Production-grade monitoring and alerting
- Designed for horizontal scalability and SaaS growth

---

##  Status

**2025 – Present**  
Actively developed and evolving as a full-scale SaaS product.
