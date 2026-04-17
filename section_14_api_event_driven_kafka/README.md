Microservice-learning — Section 14 (Event-driven / Kafka)

Overview

This repository is a Spring Boot microservices demo for a simple banking domain. Services included:
- accounts: Customer & account CRUD, Feign clients to loans/cards, and a Consumer/Producer for communication messages
- loans: Loan CRUD APIs
- cards: Card CRUD APIs
- gatewayserver: Spring Cloud Gateway (routing, fallback)
- configserver: Centralized configuration
- eurekaserver: Service registry
- message: Spring Cloud Function-based message handlers (email, sms)

Architecture & Communication

- Primary API communication: REST endpoints exposed by each service under /api.
- Service discovery & routing: Eureka + API Gateway. Gateway imports config from Config Server.
- Inter-service REST calls: OpenFeign (accounts -> loans/cards).
- Event-driven messaging: Spring Cloud Stream with Kafka binder. Topics used:
  - send-communication
  - communication-sent
  Flow: accounts -> (send-communication) -> message (email|sms) -> (communication-sent) -> accounts

Message Functions

- message service defines two Function beans: email (AccountsMsgDto->AccountsMsgDto) and sms (AccountsMsgDto->Long).
- accounts service defines a Consumer<Long> bean (updateComminication) to receive processed messages.
- Note: configuration typos (e.g., "defiition") may break function binding. Binding names must match conventions (function definition and stream.bindings).

API Summary (high-level)

Accounts (base /api):
- POST /api/create            : create account (CustomerDto)
- GET  /api/fetch             : fetch account (mobileNumber query)  <-- missing @RequestParam in code
- PUT  /api/update            : update account (CustomerDto)
- DELETE /api/delete          : delete account (mobileNumber)
- GET /api/buildVersion, /api/getBuildInfoFallback, /api/java-version, /api/get-contact-info
- GET /api/fetchCustomerDetails (in CustomerController)

Loans (base /api):
- POST /api/create
- GET  /api/fetch (requires learning-correlation-id header)
- PUT  /api/update
- DELETE /api/delete
- GET /api/java-version, /api/contact-info

Cards (base /api):
- POST /api/create
- GET  /api/fetch (requires learning-correlation-id header)
- PUT  /api/update
- DELETE /api/delete
- GET /api/java-version, /api/contact-info

Gateway:
- /contactSupport fallback endpoint

Frontend suggestion

- Framework: React + TypeScript (or Angular if you prefer opinionated structure).
- State: Redux Toolkit or Zustand for global state; react-query for server state (caching, retries).
- Structure: Dashboard, Accounts, Loans, Cards, Messaging/Activity components; each module has list, detail, create/update forms.
- Integrate with API Gateway: frontend calls gateway routes (single origin), gateway forwards to services. Use JWT in Authorization header; gateway validates tokens.

Improvements & Missing Features (high level)

- Fix function/binding typos: spring.cloud.function.definition, stream.binding names, and consistent bean names.
- Security: add OAuth2 / Keycloak or Auth0 integration; gateway as resource server; issue JWTs.
- Observability: centralize logs (ELK), distributed tracing (OpenTelemetry), metrics (Prometheus + Grafana). Otel agent already referenced in poms.
- Error handling: consistent error DTOs, exception handlers (@ControllerAdvice), and API versioning (e.g., /api/v1/)
- Missing banking features: transaction history, transfers, user profile management, audit logs, notifications, rate limits, KYC checks.
- Resilience: use timeouts, retries, bulkheads; separate Kafka topics per message type; increase consumer groups for scaling.

Code-quality notes

- Some controllers omit @RequestParam on query parameters (inconsistent). Ensure annotations present.
- Naming typos: updateComminication vs updateCommunication; config keys spelled incorrectly — fix to avoid runtime issues.
- Use DTO validation consistently; centralize constants and responses; avoid returning raw Exception messages.
- Prefer explicit API versioning and RESTful resource paths (e.g., /api/v1/accounts/{id}).

How to run (local)

1. Start Kafka & Zookeeper (docker-compose provided?).
2. Start configserver (port 8071) -> eurekaserver (8070) -> gateway (port from yml) -> services (accounts 8080, loans 8072?, cards 8072?).
3. Ensure application.yml service ports do not conflict.

Contact

For deeper changes (fixing typos, implementing security, or generating OpenAPI docs), request the specific task and files to modify.
