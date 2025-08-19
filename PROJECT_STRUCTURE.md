# 🏗️ Project Structure - Spring Boot 30 Days Challenge

## 📁 Complete Directory Structure

```
spring-boot-refactoring-challenge/
├── 📄 README.md                           # Main project documentation
├── 📄 LICENSE                             # MIT License
├── 📄 .gitignore                          # Git ignore rules
├── 📄 docker-compose.yml                  # Local development environment
├── 📄 Dockerfile                          # Production container
│
├── 📁 app/                                # Main Spring Boot application
│   ├── 📄 pom.xml                        # Maven dependencies
│   ├── 📄 README.md                      # App-specific documentation
│   │
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/example/app/
│   │   │   │   ├── 📄 App.java           # Main application class
│   │   │   │   │
│   │   │   │   ├── 📁 shared/            # Cross-cutting concerns
│   │   │   │   │   ├── 📁 domain/        # Base domain classes
│   │   │   │   │   │   ├── 📄 Entity.java
│   │   │   │   │   │   ├── 📄 ValueObject.java
│   │   │   │   │   │   └── 📄 DomainEvent.java
│   │   │   │   │   ├── 📁 exception/     # Global exceptions
│   │   │   │   │   ├── 📁 utils/         # Utility classes
│   │   │   │   │   └── 📁 config/        # Global configurations
│   │   │   │   │
│   │   │   │   ├── 📁 modules/           # Bounded contexts (DDD)
│   │   │   │   │   ├── 📁 users/         # User management module
│   │   │   │   │   │   ├── 📁 domain/    # Domain layer
│   │   │   │   │   │   │   ├── 📄 User.java
│   │   │   │   │   │   │   ├── 📄 UserId.java
│   │   │   │   │   │   │   ├── 📄 Email.java
│   │   │   │   │   │   │   ├── 📄 UserStatus.java
│   │   │   │   │   │   │   ├── 📄 UserRepository.java (interface)
│   │   │   │   │   │   │   └── 📄 events/
│   │   │   │   │   │   │       └── 📄 UserRegisteredEvent.java
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── 📁 application/ # Application layer
│   │   │   │   │   │   │   ├── 📄 UserApplicationService.java
│   │   │   │   │   │   │   ├── 📄 commands/
│   │   │   │   │   │   │   │   └── 📄 CreateUserCommand.java
│   │   │   │   │   │   │   └── 📄 queries/
│   │   │   │   │   │   │       └── 📄 GetUserQuery.java
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── 📁 infrastructure/ # Infrastructure layer
│   │   │   │   │   │   │   ├── 📁 persistence/
│   │   │   │   │   │   │   │   ├── 📄 UserJpaRepository.java
│   │   │   │   │   │   │   │   ├── 📄 UserJpaAdapter.java
│   │   │   │   │   │   │   │   └── 📄 UserEntity.java
│   │   │   │   │   │   │   ├── 📁 mappers/
│   │   │   │   │   │   │   │   └── 📄 UserMapper.java
│   │   │   │   │   │   │   └── 📁 external/
│   │   │   │   │   │   │       └── 📄 EmailService.java
│   │   │   │   │   │   │
│   │   │   │   │   │   └── 📁 presentation/ # Presentation layer
│   │   │   │   │   │       ├── 📄 UserController.java
│   │   │   │   │   │       ├── 📁 dto/
│   │   │   │   │   │       │   ├── 📄 CreateUserRequest.java
│   │   │   │   │   │       │   └── 📄 UserResponse.java
│   │   │   │   │   │       └── 📁 exception/
│   │   │   │   │   │           └── 📄 GlobalExceptionHandler.java
│   │   │   │   │   │
│   │   │   │   │   └── 📁 orders/        # Order management module
│   │   │   │   │       ├── 📁 domain/
│   │   │   │   │       ├── 📁 application/
│   │   │   │   │       ├── 📁 infrastructure/
│   │   │   │   │       └── 📁 presentation/
│   │   │   │   │
│   │   │   │   └── 📁 payments/          # Payment processing module
│   │   │   │       ├── 📁 domain/
│   │   │   │       ├── 📁 application/
│   │   │   │       ├── 📁 infrastructure/
│   │   │   │       └── 📁 presentation/
│   │   │   │
│   │   │   └── 📁 resources/
│   │   │       ├── 📄 application.yml    # Main configuration
│   │   │       ├── 📄 application-dev.yml
│   │   │       ├── 📄 application-test.yml
│   │   │       ├── 📄 application-prod.yml
│   │   │       └── 📁 db/migration/      # Flyway migrations
│   │   │           ├── 📄 V1__Create_users_table.sql
│   │   │           ├── 📄 V2__Create_orders_table.sql
│   │   │           └── 📄 V3__Create_payments_table.sql
│   │   │
│   │   └── 📁 test/
│   │       ├── 📁 java/com/example/app/
│   │       │   ├── 📁 integration/
│   │       │   │   └── 📄 AbstractPostgresIT.java
│   │       │   │
│   │       │   └── 📁 modules/
│   │       │       ├── 📁 users/
│   │       │       │   ├── 📁 domain/
│   │       │       │   │   └── 📄 UserTest.java
│   │       │       │   ├── 📁 application/
│   │       │       │   │   └── 📄 UserApplicationServiceTest.java
│   │       │       │   └── 📁 infrastructure/it/
│   │       │       │       └── 📄 UserRepositoryIT.java
│   │       │       │
│   │       │       └── 📁 orders/
│   │       │           ├── 📁 domain/
│   │       │           ├── 📁 application/
│   │       │           └── 📁 infrastructure/it/
│   │       │
│   │       └── 📁 resources/
│   │           ├── 📄 application-test.yml
│   │           └── 📄 test-data.sql
│   │
│   └── 📁 target/                        # Compiled classes (generated)
│
├── 📁 days/                              # 30 days of exercises
│   ├── 📁 day-01-fat-controller-to-clean/
│   │   ├── 📄 README.md                  # Exercise documentation
│   │   ├── 📄 BEFORE.java               # Code before refactoring
│   │   ├── 📄 AFTER.java                # Code after refactoring
│   │   └── 📄 tests.md                  # Test scenarios
│   │
│   ├── 📁 day-02-nullpointer-service-wiring/
│   │   ├── 📄 README.md
│   │   ├── 📄 BEFORE.java
│   │   ├── 📄 AFTER.java
│   │   └── 📄 tests.md
│   │
│   ├── 📁 day-03-dtos-vs-jpa-entities/
│   │   ├── 📄 README.md
│   │   ├── 📄 BEFORE.java
│   │   ├── 📄 AFTER.java
│   │   └── 📄 tests.md
│   │
│   └── 📁 ... (days 4-30)
│
├── 📁 scripts/                           # Utility scripts
│   ├── 📄 init.sql                       # Database initialization
│   ├── 📄 seed-data.sql                  # Sample data
│   ├── 📄 make-bug.sh                    # Inject bugs for debugging
│   └── 📄 performance-test.sh            # Performance testing
│
├── 📁 postman/                           # API testing
│   ├── 📄 collection.json                # Postman collection
│   ├── 📄 environment-dev.json           # Development environment
│   └── 📄 environment-test.json          # Test environment
│
├── 📁 docs/                              # Documentation
│   ├── 📄 architecture.md                # Architecture decisions
│   ├── 📄 api-specification.md           # API documentation
│   ├── 📄 deployment-guide.md            # Deployment instructions
│   └── 📄 troubleshooting.md             # Common issues
│
└── 📁 .github/                           # GitHub configuration
    ├── 📁 workflows/
    │   ├── 📄 ci.yml                     # Continuous Integration
    │   ├── 📄 cd.yml                     # Continuous Deployment
    │   └── 📄 security-scan.yml          # Security scanning
    │
    ├── 📁 ISSUE_TEMPLATE/
    │   ├── 📄 bug_report.md
    │   └── 📄 feature_request.md
    │
    └── 📄 pull_request_template.md
```

## 🏛️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                           │
├─────────────────────────────────────────────────────────────────┤
│  Controllers (REST/GraphQL)                                     │
│  ├── UserController                                             │
│  ├── OrderController                                            │
│  └── PaymentController                                          │
│                                                                 │
│  DTOs (Data Transfer Objects)                                   │
│  ├── CreateUserRequest                                          │
│  ├── UserResponse                                               │
│  └── ErrorResponse                                              │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                   APPLICATION LAYER                             │
├─────────────────────────────────────────────────────────────────┤
│  Application Services (Use Cases)                               │
│  ├── UserApplicationService                                     │
│  ├── OrderApplicationService                                    │
│  └── PaymentApplicationService                                  │
│                                                                 │
│  Commands & Queries                                             │
│  ├── CreateUserCommand                                          │
│  ├── GetUserQuery                                               │
│  └── ProcessPaymentCommand                                      │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                                │
├─────────────────────────────────────────────────────────────────┤
│  Entities & Aggregates                                          │
│  ├── User (Entity)                                              │
│  ├── Order (Aggregate Root)                                     │
│  └── Payment (Entity)                                           │
│                                                                 │
│  Value Objects                                                  │
│  ├── Email                                                      │
│  ├── UserId                                                     │
│  └── Money                                                      │
│                                                                 │
│  Domain Services                                                │
│  ├── UserRegistrationService                                    │
│  └── PaymentValidationService                                   │
│                                                                 │
│  Repository Interfaces                                          │
│  ├── UserRepository                                             │
│  ├── OrderRepository                                            │
│  └── PaymentRepository                                          │
│                                                                 │
│  Domain Events                                                  │
│  ├── UserRegisteredEvent                                        │
│  ├── OrderCreatedEvent                                          │
│  └── PaymentProcessedEvent                                      │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                 INFRASTRUCTURE LAYER                            │
├─────────────────────────────────────────────────────────────────┤
│  Persistence                                                    │
│  ├── UserJpaRepository                                          │
│  ├── UserJpaAdapter                                             │
│  └── UserEntity                                                 │
│                                                                 │
│  External Services                                              │
│  ├── EmailService                                               │
│  ├── PaymentGatewayService                                      │
│  └── NotificationService                                        │
│                                                                 │
│  Mappers                                                        │
│  ├── UserMapper (MapStruct)                                     │
│  ├── OrderMapper                                                │
│  └── PaymentMapper                                              │
│                                                                 │
│  Configuration                                                  │
│  ├── DatabaseConfig                                             │
│  ├── SecurityConfig                                             │
│  └── CacheConfig                                                │
└─────────────────────────────────────────────────────────────────┘
```

## 🔄 Data Flow Diagram

```
┌─────────────┐    ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│  Controller  │───▶│ Application │───▶│   Domain    │
│  (Browser/  │    │              │    │  Service    │    │   Layer     │
│   Mobile)   │    │              │    │             │    │             │
└─────────────┘    └──────────────┘    └─────────────┘    └─────────────┘
                          │                     │                │
                          ▼                     ▼                ▼
                   ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
                   │     DTOs     │    │   Commands  │    │  Repository │
                   │              │    │   & Queries │    │  Interface  │
                   └──────────────┘    └─────────────┘    └─────────────┘
                                                                 │
                                                                 ▼
                   ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
                   │   External   │◀───│ Infrastructure│◀───│  Repository │
                   │   Services   │    │   Layer     │    │  Implementation │
                   │              │    │             │    │             │
                   └──────────────┘    └─────────────┘    └─────────────┘
```

## 📊 Module Dependencies

```
┌─────────────────────────────────────────────────────────────────┐
│                        MODULES                                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │    Users    │    │    Orders   │    │   Payments  │         │
│  │   Module    │    │   Module    │    │   Module    │         │
│  │             │    │             │    │             │         │
│  │ ┌─────────┐ │    │ ┌─────────┐ │    │ ┌─────────┐ │         │
│  │ │ Domain  │ │    │ │ Domain  │ │    │ │ Domain  │ │         │
│  │ └─────────┘ │    │ └─────────┘ │    │ └─────────┘ │         │
│  │ ┌─────────┐ │    │ ┌─────────┐ │    │ ┌─────────┐ │         │
│  │ │Application│ │    │ │Application│ │    │ │Application│ │         │
│  │ └─────────┘ │    │ └─────────┘ │    │ └─────────┘ │         │
│  │ ┌─────────┐ │    │ ┌─────────┐ │    │ ┌─────────┐ │         │
│  │ │Infrastructure│ │    │ │Infrastructure│ │    │ │Infrastructure│ │         │
│  │ └─────────┘ │    │ └─────────┘ │    │ └─────────┘ │         │
│  │ ┌─────────┐ │    │ ┌─────────┐ │    │ ┌─────────┐ │         │
│  │ │Presentation│ │    │ │Presentation│ │    │ │Presentation│ │         │
│  │ └─────────┘ │    │ └─────────┘ │    │ └─────────┘ │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                    SHARED KERNEL                            │ │
│  │  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐        │ │
│  │  │ Entity  │  │ValueObject│  │DomainEvent│  │ Utils  │        │ │
│  │  └─────────┘  └─────────┘  └─────────┘  └─────────┘        │ │
│  └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

## 🎯 Why This Structure?

### 1. **Domain-Driven Design (DDD)**
- **Bounded Contexts**: Each module is a bounded context
- **Ubiquitous Language**: Consistent terminology throughout the code
- **Aggregate Roots**: Main entities that control access

### 2. **Clean Architecture**
- **Dependency Rule**: Dependencies point inward
- **Separation of Concerns**: Each layer has specific responsibilities
- **Testability**: Easy to test each layer independently

### 3. **Scalability**
- **Modular**: Easy to add new modules
- **Maintainable**: Localized changes
- **Deployable**: Each module can be deployed independently

### 4. **Professional Standards**
- **Industry Best Practices**: Structure recognized by the community
- **Team Collaboration**: Easy for teams to work in parallel
- **Code Review**: Clear structure for reviews

## 🚀 Benefits for LinkedIn

### 1. **Technical Credibility**
- Demonstrates knowledge of enterprise architectures
- Shows experience with design patterns
- Evidences software design skills

### 2. **Professional Image**
- Structured and organized code
- Complete and professional documentation
- Automated tests and CI/CD

### 3. **Career Growth**
- Portfolio that stands out in interviews
- Concrete examples of senior skills
- Demonstration of architectural thinking

---

**Ready to implement this structure?** 🚀

This architecture will position you as a senior developer who understands not just how to write code, but how to design scalable and maintainable systems.
