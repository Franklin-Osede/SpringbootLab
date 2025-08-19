# 🚀 Spring Boot Refactoring & Debugging 

> **30 days of practical refactoring and debugging exercises in Spring Boot to demonstrate senior development skills**

## 📋 Description

This project contains 30 daily refactoring and debugging exercises in Spring Boot, designed to showcase advanced development skills. Each day includes:

- **Real problems** that demonstrate senior experience
- **Step-by-step solutions** with best practices
- **Complete tests** with JUnit 5 + Testcontainers
- **DDD Architecture** with design patterns
- **Before and after code** for LinkedIn

## 🏗️ Project Structure

```
spring-boot-refactoring-challenge/
├── app/                          # Main Spring Boot application
│   ├── src/main/java/com/example/app/
│   │   ├── shared/               # Cross-cutting concerns (VOs, Result, utils)
│   │   ├── modules/              # Bounded contexts (DDD)
│   │   │   └── users/            # Example module
│   │   │       ├── domain/       # Entities, VOs, events, repositories
│   │   │       ├── application/  # Use cases, DTOs
│   │   │       ├── infrastructure/ # JPA, Web, Config, Mappers
│   │   │       └── presentation/ # Controllers
│   │   └── App.java
│   ├── src/test/java/            # Unit and integration tests
│   ├── pom.xml                   # Maven dependencies
│   └── README.md                 # Setup and configuration
├── days/                         # 30 days of exercises
│   ├── day-01-fat-controller-to-clean/
│   ├── day-02-nullpointer-service-wiring/
│   └── ... (up to day-30)
├── scripts/                      # Utility scripts
├── postman/                      # API endpoint collection
├── docker-compose.yml           # Local environment
└── .github/workflows/ci.yml     # CI/CD
```

## 🎯 The 30 Days

### Week 1: Refactoring Fundamentals
- **Day 01**: Fat Controller → Clean Controller
- **Day 02**: NullPointer in Service Wiring
- **Day 03**: DTOs vs JPA Entities in API
- **Day 04**: Magic Numbers → Constants/Enums
- **Day 05**: Custom Exceptions + ControllerAdvice
- **Day 06**: Giant if-else → Strategy/Polymorphism
- **Day 07**: Bean Validation

### Week 2: Persistence & Transactions
- **Day 08**: Transaction Boundaries and Consistency
- **Day 09**: Jackson Serialization Bug (Infinite Loop)
- **Day 10**: N+1 Queries: Detection and Fix
- **Day 11**: Effective Logging for Debugging
- **Day 12**: Endpoint Performance Measurements
- **Day 13**: External Client Resilience
- **Day 14**: Clean Configuration: Profiles and @ConfigurationProperties

### Week 3: Patterns & Optimization
- **Day 15**: Readable Repository Queries
- **Day 16**: Fat Mapper → MapStruct + Tests
- **Day 17**: Deadlock Prevention
- **Day 18**: WebClient Error Handling
- **Day 19**: Idempotent Endpoints
- **Day 20**: Cross-cutting Concerns with AOP
- **Day 21**: Proper Pagination and Sorting

### Week 4: Domain & Validation
- **Day 22**: Domain Validation: Value Objects
- **Day 23**: Shared State and Race Conditions
- **Day 24**: Local Caching (Caffeine)
- **Day 25**: Rich Domain Models
- **Day 26**: Feature Flags for Safe Rollouts
- **Day 27**: Data Integrity Validation
- **Day 28**: Time Control and Deterministic Tests

### Week 5: Production & DevOps
- **Day 29**: Database Migrations with Flyway
- **Day 30**: Production Readiness Checklist

## 🛠️ Technologies

### Core
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL** (Testcontainers for tests)

### Testing (MANDATORY)
- **JUnit 5**
- **Testcontainers**
- **AssertJ**
- **Mockito**

### Architecture
- **DDD (Domain Driven Design)**
- **Clean Architecture**
- **Design Patterns** (Strategy, Factory, Repository, etc.)
- **MapStruct** (Mappers)

### Quality
- **Spotless** (code formatting)
- **Checkstyle** (code rules)
- **JaCoCo** (coverage)

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker (for Testcontainers)

### Local Setup
```bash
# Clone repository
git clone <repo-url>
cd spring-boot-refactoring-challenge

# Start services (optional)
docker-compose up -d

# Run tests
mvn test

# Run application
mvn spring-boot:run
```

### Test Structure
```bash
# Unit tests
mvn test -Dtest="*Test"

# Integration tests
mvn test -Dtest="*IT"

# All tests
mvn test
```

## 📊 Quality Metrics

- **Code coverage**: >80%
- **Integration tests**: 100% with Testcontainers
- **DDD patterns**: Applied in all modules
- **Performance**: Before/after measurements

## 🎥 For LinkedIn

Each day includes:
- **Attractive hook** (1 line)
- **Real problem** (2 lines)
- **Technical solution** (3 bullets)
- **Measurable result** (1 data point)
- **CTA** (repo + hashtags)

## 🤝 Contributing

1. Fork the project
2. Create a branch for your day: `git checkout -b day-XX-topic`
3. Implement the exercise following conventions
4. Ensure all tests pass
5. Create a Pull Request

## 📝 Conventions

### Commits
```
feat(day-01): refactor controller to follow single responsibility
fix(day-02): resolve nullpointer with proper dependency injection
test(day-03): add integration tests with testcontainers
```

### Naming
- **Unit tests**: `*Test.java`
- **Integration tests**: `*IT.java`
- **Domain classes**: No suffixes
- **JPA entities**: `*Entity.java`
- **DTOs**: `*Dto.java`

### DDD Structure
- **Domain**: Entities, VOs, events, repositories (interfaces)
- **Application**: Use cases, commands, queries
- **Infrastructure**: Implementations, adapters
- **Presentation**: Controllers, input/output DTOs

## 📚 Resources

- [Spring Boot Reference](https://spring.io/projects/spring-boot)
- [Testcontainers](https://www.testcontainers.org/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

---

**Ready for the 30 days?** 🚀

Each day is an opportunity to demonstrate senior skills on LinkedIn. Let's start!
