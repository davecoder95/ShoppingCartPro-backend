# ShopCartPro Backend

ShopCartPro is a Spring Boot REST API for a shopping cart application, built incrementally with PostgreSQL persistence, testing, API documentation, security, and containerization.

## Project Status

Current development includes:

* Product CRUD REST APIs
* Order management
* User management
* PostgreSQL + Spring Data JPA / Hibernate
* Unit testing with JUnit 5 and Mockito
* Integration testing with MockMvc
* Swagger / OpenAPI
* Spring Security
* BCrypt password hashing
* JWT authentication
* Stateless authentication
* Docker support


# Details 
## Project Status

Current development includes:

* **Product CRUD REST APIs** — Create, read, update, and delete products through REST endpoints.
* **Order management** — Create and retrieve customer orders with multiple order items, quantities, prices, totals, and order status.
* **User management** — User registration and CRUD operations using request/response DTOs.
* **PostgreSQL + Spring Data JPA / Hibernate** — Persistent relational data storage with entity mapping and repository-based database access.
* **Unit testing with JUnit 5 and Mockito** — Isolated service-layer testing using mocked dependencies.
* **Integration testing with MockMvc** — Testing REST endpoints through the Spring application context.
* **Swagger / OpenAPI** — Interactive API documentation and endpoint testing.
* **Spring Security** — Authentication and endpoint protection for the REST API.
* **BCrypt password hashing** — User passwords are securely hashed before being stored in the database.
* **JWT authentication** — Login generates a signed JWT used to authenticate subsequent requests.
* **Stateless authentication** — Authentication is maintained through JWT Bearer tokens rather than server-side HTTP sessions.
* **Docker support** — Spring Boot application packaged and run as a Docker container with external PostgreSQL connectivity.


Future development includes role-based authorization, Docker Compose, CI/CD, and AWS deployment.

## Technology Stack

* Java
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit 5
* Mockito
* MockMvc
* Swagger / OpenAPI
* Spring Security
* JWT
* Docker
* Git / GitHub

## Project Structure

```text
shopcartpro/
├── src/
│   ├── main/
│   │   ├── java/com/niva/shopcartpro/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ShopcartProApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker/
├── pom.xml
└── README.md
```

## APIs

### Product

```text
POST   /products
GET    /products
GET    /products/{id}
PUT    /products/{id}
DELETE /products/{id}
```

### Orders

```text
POST   /orders
GET    /orders
GET    /orders/{id}
DELETE /orders/{id}
```

Orders require authentication.

### Users

```text
POST   /users
GET    /users
GET    /users/{id}
DELETE /users/{id}
```

User responses use DTOs so passwords are not exposed.

### Authentication

```text
POST /auth/login
```

Login authenticates the user and returns a JWT.

Protected requests use:

```http
Authorization: Bearer <JWT>
```

## Database

PostgreSQL is used for persistent application data.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Spring Data JPA
    ↓
Hibernate
    ↓
PostgreSQL
```

Current entities include:

```text
Product
Order
OrderItem
User
```

## Security

Spring Security provides authentication and endpoint protection.

Passwords are stored using BCrypt hashing.

JWT authentication is stateless:

```text
Login
  ↓
Email + Password
  ↓
Spring Security + BCrypt
  ↓
JWT
  ↓
Bearer Token
  ↓
JWT Filter
  ↓
Authenticated Request
```

The JWT signing secret is externalized through application configuration rather than hard-coded in Java.

Current endpoint security:

```text
/auth/**       → public
/products/**   → public
/users/**      → public
/orders/**     → authenticated
Everything else → authenticated
```

Role-based authorization is planned for later.

## Testing

The project contains unit and integration tests.

### Unit Testing

`ProductServiceTest` uses JUnit 5 and Mockito to test service-layer behavior independently from the database.

### Integration Testing

`ProductControllerIntegrationTest` uses Spring Boot and MockMvc to test the application through the controller layer.

## Swagger / OpenAPI

Swagger UI is available locally at:

```text
/swagger-ui/index.html
```

It provides interactive API documentation and API testing.

## Docker

The Spring Boot application can be containerized with Docker.

Current architecture:

```text
Docker
└── Spring Boot Application
        ↓
    PostgreSQL
     (external)
```

PostgreSQL currently runs separately from the application container.

Docker configuration supports environment-based database configuration.

Future Docker work includes:

* PostgreSQL container
* Docker Compose
* Persistent volumes
* Environment configuration

## Running Locally

Start PostgreSQL with the database:

```text
shopcartpro_db
```

Run the application:

```powershell
.\mvnw.cmd spring-boot:run
```

Run tests:

```powershell
.\mvnw.cmd test
```

The application runs on port `8080`.

## API Testing

The API can be tested using:

* Swagger UI
* Postman
* Automated tests

## Development Roadmap

```text
Spring Boot Setup
       ↓
Product CRUD
       ↓
PostgreSQL + JPA
       ↓
Unit Testing
       ↓
Swagger / OpenAPI
       ↓
Integration Testing
       ↓
Order Module
       ↓
User Module
       ↓
Spring Security
       ↓
JWT Authentication
       ↓
Docker
       ↓
Role-Based Authorization
       ↓
Docker Compose
       ↓
CI/CD
       ↓
AWS Deployment
```

## Planned AWS Architecture

The eventual AWS deployment may include:

* EC2 for Spring Boot
* RDS PostgreSQL
* IAM
* CloudWatch
* S3
* Lambda for selected event-driven functionality

## Git Workflow

The project uses Git and GitHub with incremental commits for major milestones.

Example commits:

```text
Connect product CRUD to PostgreSQL
Add product tests and OpenAPI documentation
Add order module
Add user module and BCrypt authentication
Add JWT authentication
Dockerize Spring Boot application
```

## Future Enhancements

* Request validation
* Global exception handling
* Improved API error documentation
* Role-based access control
* Docker Compose
* CI/CD
* AWS deployment
* Production configuration
* Monitoring and logging
