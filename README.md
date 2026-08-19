# Gallery — Microservices

A microservices backend for an art-gallery domain, built with **Spring Boot** and **Spring Cloud**. The system is split into independent services behind an API gateway, with service discovery, centralized configuration, JWT authentication, and full Docker Compose orchestration.

> Backend rebuild of a gallery management system, moving from a monolith to a distributed architecture to explore service decomposition, inter-service communication, and containerized deployment.

## Architecture

```
                    ┌─────────────┐
   client ────────▶ │ api-gateway │  (routing + JWT validation)
                    └──────┬──────┘
                           │
        ┌──────────┬───────┼───────────┬──────────────┐
        ▼          ▼       ▼           ▼              ▼
   auth-service  catalog  exhibition  order-service   ...
                 -service -service
        │          │       │           │
        └──────────┴───────┴───────────┘
             OpenFeign (service-to-service calls)

   eureka-server  ← all services register for discovery
   config-server  ← serves centralized config (port 8888)
   MySQL          ← per-service persistence
```

## Services

| Service | Responsibility |
|---|---|
| `api-gateway` | Single entry point; request routing and JWT validation |
| `auth-service` | User authentication and JWT issuance |
| `catalog-service` | Artworks / catalog domain |
| `exhibition-service` | Exhibitions domain |
| `order-service` | Orders domain |
| `eureka-server` | Service discovery / registry |
| `config-server` | Centralized configuration (Spring Cloud Config) |

Each domain service follows the same layered layout: `controller → service → repository`, with `dto`, `model`, `client` (Feign), and `exception` packages.

## Tech Stack

- **Java 21**, **Spring Boot**, **Spring Cloud** (`2025.1.x`)
- **Spring Cloud Gateway** — API gateway
- **Netflix Eureka** — service discovery
- **Spring Cloud Config** — centralized configuration
- **OpenFeign** — declarative inter-service HTTP clients
- **MySQL** — persistence (one schema per service)
- **Docker Compose** — orchestrates all services + MySQL
