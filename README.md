# ShopCartPro Backend

ShopCartPro is a Spring Boot REST API for a shopping cart application. The project is being built incrementally with a focus on clean backend architecture, database persistence, testing, API documentation, containerization, and cloud deployment.

## Project Status

Current development includes:

* Product CRUD REST APIs
* PostgreSQL database integration
* Spring Data JPA / Hibernate
* Unit testing with JUnit and Mockito
* Integration testing with MockMvc
* Swagger / OpenAPI documentation
* Docker support for the Spring Boot application

Future development will include application security, Docker Compose, and AWS deployment.

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
* Docker
* Git / GitHub

## Project Structure

```text
shopcartpro/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/niva/shopcartpro/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── ShopcartProApplication.java
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/niva/shopcartpro/
│               ├── controller/
│               ├── service/
│               └── ShopcartproApplicationTests.java
│
├── docker/
│   └── Dockerfile
│
├── pom.xml
└── README.md
```

## Product API

### Create Product

```http
POST /products
```

Example request:

```json
{
  "name": "Keyboard",
  "price": 59.99
}
```

### Get All Products

```http
GET /products
```

### Get Product by ID

```http
GET /products/{id}
```

### Update Product

```http
PUT /products/{id}
```

Example request:

```json
{
  "name": "Mechanical Keyboard",
  "price": 89.99
}
```

### Delete Product

```http
DELETE /products/{id}
```

## Database

The application uses PostgreSQL for persistent product data.

Spring Boot connects to PostgreSQL through Spring Data JPA and Hibernate.

```text
ProductController
        ↓
ProductService
        ↓
ProductRepository
        ↓
Spring Data JPA
        ↓
Hibernate
        ↓
PostgreSQL
```

Hibernate automatically manages the database table structure based on the JPA entity configuration during development.

## Testing

The project contains both unit and integration tests.

### Unit Testing

`ProductServiceTest` uses JUnit 5 and Mockito to test service-layer behavior independently from the database.

Examples include:

* Retrieving products
* Retrieving a product by ID
* Handling a product-not-found scenario

### Integration Testing

`ProductControllerIntegrationTest` uses Spring Boot and MockMvc to test the API through the controller layer.

The integration test verifies the complete application flow:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
Hibernate / JPA
     ↓
PostgreSQL
```

## Swagger / OpenAPI

Interactive API documentation is available through Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for viewing and testing the REST API, including request and response models.

## Docker

Docker is being introduced to containerize the Spring Boot application.

The initial Docker setup will containerize the application while PostgreSQL continues to run separately.

```text
Docker
└── Spring Boot Application
        ↓
    PostgreSQL
    (external)
```

The Docker configuration is kept separately under the `docker/` directory so the existing application source structure remains clean.

Docker will eventually be expanded to support:

* Spring Boot container
* PostgreSQL container
* Docker Compose
* Environment-based configuration
* Persistent database storage

## Running Locally

### Start PostgreSQL

Create a PostgreSQL database:

```text
shopcartpro_db
```

### Run the Application

Using the Maven wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

### Run Tests

```powershell
.\mvnw.cmd test
```

## API Testing

The API can be tested using:

* Swagger UI
* Postman
* Automated integration tests

## Development Roadmap

The project is being developed incrementally.

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
Docker
       ↓
Spring Security
       ↓
Docker Compose
       ↓
AWS Deployment
```

### Planned AWS Architecture

The eventual AWS deployment may include:

* EC2 for the Spring Boot application
* RDS PostgreSQL for the database
* IAM for access control
* CloudWatch for monitoring and logs
* S3 for file/object storage
* Lambda for selected event-driven functionality

## Git Workflow

The project is maintained using Git and GitHub with incremental commits representing major development milestones.

Example milestones:

```text
Connect product CRUD to PostgreSQL
Add product tests and OpenAPI documentation
Dockerize Spring Boot application
```

## Future Enhancements

Planned improvements include:

* Request validation
* Global exception handling
* Improved API error documentation
* Spring Security
* JWT authentication and authorization
* Role-based access control
* Docker Compose
* CI/CD
* AWS deployment
* Production configuration and monitoring
