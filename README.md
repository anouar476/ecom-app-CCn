# E‑Commerce Microservices (Spring Cloud)

This repo contains a minimal microservices stack with service discovery (Eureka), an API Gateway, and two domain services (Customer and Product/Inventory). It demonstrates both static routing and dynamic routing through Spring Cloud Gateway.

## Modules and Ports

- discoveryService (Eureka Server)
  - Port: 8761
  - UI: http://localhost:8761
- GateWayService (Spring Cloud Gateway)
  - Port: 8888
- customer-service
  - Port: 8081
  - Spring Data REST base path: /api
- inventory-service (named product-service)
  - Port: 8082
  - Spring Data REST base path: /api

Notes:
- customer-service registers with name: `customer-service`
- product-service registers with name: `product-service`

## How to Run (local)

Open four terminals and run one service per terminal, or start them in order:

1) Discovery Server

```bash
cd "discoveryService"
./mvnw spring-boot:run
```

2) Customer Service

```bash
cd "customer-service"
./mvnw spring-boot:run
```

3) Product/Inventory Service

```bash
cd "inventory-service"
./mvnw spring-boot:run
```

4) API Gateway

```bash
cd "GateWayService"
./mvnw spring-boot:run
```

Once started, go to the Eureka dashboard and verify that the instances are registered: http://localhost:8761

## Routes through the Gateway

The gateway is configured for BOTH static and dynamic routing.

- Static routes (configured explicitly in `GateWayService/src/main/resources/application.yml`):
  - http://localhost:8888/api/customers/**  → `CUSTOMER-SERVICE`
  - http://localhost:8888/api/products/**   → `PRODUCT-SERVICE`

- Dynamic routes (discovery locator enabled):
  - Pattern: http://localhost:8888/{service-id}/**
  - For this project, use lowercase service IDs because the services register as lowercase names:
    - http://localhost:8888/customer-service/api/customers
    - http://localhost:8888/product-service/api/products

Why the difference?
- Static routing matches the path you define (e.g., `/api/customers/**`) and forwards that same path to the backing service.
- Dynamic routing prefixes the path with the discovered service ID (e.g., `/customer-service/**`) to let the gateway route to any service without adding a static route per service.

Tip about case-sensitivity:
- Current gateway config sets `spring.cloud.gateway.discovery.locator.lower-case-service-id: false`.
- Your services register as `customer-service` and `product-service` (lowercase), so the dynamic URL must also be lowercase. A request to `/CUSTOMER-SERVICE/...` won’t match and will return 404.
- If you prefer lowercase dynamic URLs always, set `lower-case-service-id: true` and then use `/customer-service/...` consistently.

## Quick Test Commands

- Static routes:
  - `curl http://localhost:8888/api/customers`
  - `curl http://localhost:8888/api/products`

- Dynamic routes (lowercase service id):
  - `curl http://localhost:8888/customer-service/api/customers`
  - `curl http://localhost:8888/product-service/api/products`

If you see a 404, check the troubleshooting section below.

## Troubleshooting (404 / Whitelabel / Not Found)

- Calling `http://localhost:8888/CUSTOMER-SERVICE/api/customers` returns 404:
  - Cause: service ID case mismatch. The dynamic path segment must match the registered service ID. In this project, it’s lowercase (`customer-service`). Use: `http://localhost:8888/customer-service/api/customers`.
  - Alternative: set `spring.cloud.gateway.discovery.locator.lower-case-service-id=true` to normalize to lowercase.

- No route matches your path:
  - Ensure the static route exists if you’re using `/api/...` paths (it’s already configured for customers and products).
  - For dynamic routes, use the service ID prefix: `/{service-id}/...`. Verify the actual service IDs on the Eureka dashboard.

- Service not registered / not running:
  - Open http://localhost:8761 and confirm `customer-service` and `product-service` appear as UP.
  - Start missing services (`./mvnw spring-boot:run`).

- Gateway forwards but backend still 404:
  - The domain services expose Spring Data REST under `/api`. Access paths like `/api/customers` or `/api/products` behind the gateway.

## Data and Consoles

- In-memory H2 databases (dev only):
  - customer-service: jdbc:h2:mem:customers-db (H2 console enabled)
  - product-service: jdbc:h2:mem:products-db (H2 console enabled)
- H2 console is available on each service (not via gateway unless you add a route):
  - http://localhost:8081/h2-console (customer)
  - http://localhost:8082/h2-console (product)

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Spring Cloud 2023.0.3
- Spring Cloud Netflix Eureka (service discovery)
- Spring Cloud Gateway (API Gateway)
- Spring Data REST (exposes `/api` endpoints)
- H2 in-memory databases (dev)

## Minimal Screenshots (optional)

Add screenshots only if needed for a report; not required for running the project:
- Eureka dashboard showing registered services
- Sample GET to `/api/customers` via the gateway

## Project Structure (high-level)

- `discoveryService/` – Eureka Server
- `GateWayService/` – API Gateway (`application.yml` contains routes)
- `customer-service/` – Customer domain (Spring Data REST under `/api`)
- `inventory-service/` – Product domain (Spring Data REST under `/api`, registers as `product-service`)

## Next Steps (optional)

- If you want dynamic routes to always be lowercase, set:

```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
```

…and use `/customer-service/...` and `/product-service/...` consistently.

- Add gateway filters (e.g., CircuitBreaker, Retry) and security as needed.

