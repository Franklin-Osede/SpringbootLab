# 🚀 Spring Boot Application - 30 Days Challenge

Esta es la aplicación base para el desafío de 30 días de refactoring y debugging en Spring Boot.

## 🏗️ Arquitectura

La aplicación sigue los principios de **Domain Driven Design (DDD)** y **Clean Architecture**:

```
com.example.app/
├── shared/                    # Kernel transversal
│   └── domain/               # Clases base (Entity, ValueObject, DomainEvent)
├── modules/                   # Bounded contexts
│   └── users/                # Módulo de usuarios (ejemplo)
│       ├── domain/           # Entidades, VOs, eventos, repositorios (interfaces)
│       ├── application/      # Casos de uso, comandos, queries
│       ├── infrastructure/   # JPA, Web, Config, Mappers
│       └── presentation/     # Controllers, DTOs de entrada/salida
└── App.java                  # Clase principal
```

## 🛠️ Tecnologías

### Core
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL** (producción) / **H2** (tests)

### Testing (MANDATORIO)
- **JUnit 5**
- **Testcontainers**
- **AssertJ**
- **Mockito**

### Arquitectura
- **DDD (Domain Driven Design)**
- **Clean Architecture**
- **MapStruct** (Mappers)

### Calidad
- **Spotless** (formateo)
- **Checkstyle** (reglas)
- **JaCoCo** (cobertura)

## 🚀 Quick Start

### Prerrequisitos
- Java 17+
- Maven 3.8+
- Docker (opcional, para PostgreSQL local)

### Setup Local

1. **Clonar y navegar al directorio**
   ```bash
   cd app
   ```

2. **Levantar servicios (opcional)**
   ```bash
   # Desde el directorio raíz del proyecto
   docker-compose up -d
   ```

3. **Ejecutar tests**
   ```bash
   # Todos los tests
   mvn test
   
   # Solo tests unitarios
   mvn test -Dtest="*Test"
   
   # Solo tests de integración
   mvn test -Dtest="*IT"
   ```

4. **Ejecutar aplicación**
   ```bash
   mvn spring-boot:run
   ```

5. **Verificar que funciona**
   ```bash
   curl http://localhost:8080/api/v1/actuator/health
   ```

### Profiles Disponibles

- **dev** (por defecto): Desarrollo local con PostgreSQL
- **test**: Tests con H2 en memoria
- **prod**: Producción con variables de entorno

```bash
# Ejecutar con profile específico
mvn spring-boot:run -Dspring.profiles.active=dev
```

## 📊 Endpoints Disponibles

### Health Check
- `GET /api/v1/actuator/health` - Estado de la aplicación
- `GET /api/v1/actuator/info` - Información de la aplicación
- `GET /api/v1/actuator/metrics` - Métricas de la aplicación

### Base de Datos
- **Local**: `jdbc:postgresql://localhost:5432/spring30days`
- **pgAdmin**: http://localhost:8081 (admin@example.com / admin)

## 🧪 Testing

### Estructura de Tests
```
src/test/java/
├── integration/
│   └── AbstractPostgresIT.java    # Base para tests de integración
└── modules/
    └── users/
        ├── domain/                # Tests unitarios del dominio
        ├── application/           # Tests unitarios de aplicación
        └── infrastructure/it/     # Tests de integración
```

### Convenciones
- **Tests unitarios**: `*Test.java` (sin contexto Spring)
- **Tests integración**: `*IT.java` (con Testcontainers)
- **Naming**: `should_do_something_when_condition()`

### Ejecutar Tests
```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=UserServiceTest
mvn test -Dtest=UserRepositoryIT

# Con cobertura
mvn test jacoco:report
```

## 🔧 Comandos Útiles

### Maven
```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Formatear código
mvn spotless:apply

# Verificar estilo
mvn checkstyle:check

# Generar documentación
mvn javadoc:javadoc

# Ejecutar con profile
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Docker
```bash
# Levantar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar servicios
docker-compose down

# Limpiar volúmenes
docker-compose down -v
```

## 📈 Métricas y Monitoreo

### Actuator Endpoints
- `/actuator/health` - Estado de salud
- `/actuator/metrics` - Métricas de la aplicación
- `/actuator/prometheus` - Métricas en formato Prometheus

### Logs
- **Desarrollo**: Console con formato detallado
- **Producción**: Archivo `logs/application.log`

### Cobertura de Código
- **Mínimo**: 80%
- **Reporte**: `target/site/jacoco/index.html`

## 🐛 Debugging

### Configuración de Logs
```yaml
logging:
  level:
    com.example.app: DEBUG
    org.hibernate.SQL: DEBUG
    org.springframework.web: DEBUG
```

### Debug Remoto
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📝 Convenciones de Código

### Naming
- **Clases**: PascalCase (`UserService`)
- **Métodos**: camelCase (`createUser`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_RETRY_ATTEMPTS`)
- **Packages**: lowercase (`com.example.app.users`)

### Estructura DDD
- **Domain**: Entidades, VOs, eventos, repositorios (interfaces)
- **Application**: Casos de uso, comandos, queries
- **Infrastructure**: Implementaciones, adaptadores
- **Presentation**: Controllers, DTOs de entrada/salida

### Commits
```
feat: add user registration functionality
fix: resolve nullpointer in user service
test: add integration tests for user repository
refactor: extract user validation logic
docs: update API documentation
```

## 🔒 Seguridad

### Variables de Entorno
```bash
# Base de datos
DATABASE_URL=jdbc:postgresql://localhost:5432/spring30days
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

# Aplicación
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

### Configuración de Seguridad
- **Dev**: Sin autenticación
- **Prod**: Variables de entorno obligatorias
- **Tests**: H2 en memoria

## 🚀 Despliegue

### Docker
```bash
# Construir imagen
docker build -t spring30days .

# Ejecutar contenedor
docker run -p 8080:8080 spring30days
```

### Producción
```bash
# Construir JAR
mvn clean package -Pprod

# Ejecutar
java -jar target/spring-refactor-debug-30days-1.0.0.jar
```

## 📚 Recursos Adicionales

- [Spring Boot Reference](https://spring.io/projects/spring-boot)
- [Testcontainers](https://www.testcontainers.org/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

**¡Listo para empezar los 30 días!** 🚀

Cada día añadirá nuevos ejercicios y funcionalidades a esta aplicación base.
