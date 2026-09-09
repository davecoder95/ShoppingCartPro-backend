========================================
SHOPCARTPRO - PROJECT SUMMARY
=============================

This file summarizes what was built and learned during the
ShopCartPro Spring Boot backend project.

Project type:
Monolithic Spring Boot REST API

Main technologies:

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Git / GitHub
* Docker
* Spring Security


Built a Spring Boot REST API backend for an e-commerce application called ShopCartPro.
Structured the project using Controller → Service → Repository → Database architecture.
Created the Product module with full CRUD operations:
Create, Read, Update, and Delete.
Created the Order module with Order and OrderItem entities and tested order creation and retrieval.
Created the User module with CUSTOMER and ADMIN roles.
Connected the Spring Boot application to a PostgreSQL database named shopcartpro_db.
Used Spring Data JPA and Hibernate to map Java entities to PostgreSQL tables.
Tested Product, Order, and User REST APIs using JSON request data.
Learned Maven commands to run, test, build, and package the Spring Boot application as a JAR.
Used Git and GitHub for version control, including staging, committing, pulling, and pushing project changes.
Created a Dockerfile and packaged the Spring Boot application into a Docker image.
Created and managed a Docker container running the ShopCartPro backend.
Connected the Dockerized backend to PostgreSQL running on the Windows host using host.docker.internal.
Practiced Docker volumes safely using a separate test volume and learned the difference between containers, images, and persistent volumes.
Final security phase: implement Spring Security with password hashing, authentication, authorization, and CUSTOMER/ADMIN role-based access control.

PROJECT ARCHITECTURE:

Spring Boot Monolith
↓
Product + Order + User
↓
Spring Data JPA
↓
PostgreSQL

Docker:
Spring Boot JAR
↓
Docker Image
↓
Docker Container
↓
PostgreSQL on Host


========================================

1. PROJECT SETUP
   ========================================

* Created a Spring Boot backend project.
* Used Java as the programming language.
* Organized the application using packages:

  * model
  * repository
  * service
  * controller
* Configured the application to use PostgreSQL.
* Created the ShopCartPro database.
* Used Maven for building and running the project.

========================================
2. SPRING BOOT STRUCTURE
========================

Learned the basic Spring Boot application architecture:

Controller
↓
Service
↓
Repository
↓
Database

Controller:

* Handles HTTP requests.
* Defines API endpoints.

Service:

* Contains application/business logic.
* Calls repositories.

Repository:

* Handles database operations.
* Uses Spring Data JPA.

Model / Entity:

* Represents database tables.

========================================
3. PRODUCT MODULE
=================

Created the Product entity.

Product fields:

* id
* name
* price

Database:

* Product data is stored in PostgreSQL.

Implemented Product APIs:

* Create product
* Get all products
* Get product by ID
* Update product
* Delete product

REST endpoint:

/products

Learned:

* @Entity
* @Id
* @GeneratedValue
* @Column
* @RestController
* @RequestMapping
* @GetMapping
* @PostMapping
* @PutMapping
* @DeleteMapping
* @PathVariable
* @RequestBody

========================================
4. ORDER MODULE
===============

Created the Order entity.

Order fields:

* id
* orderDate
* totalAmount
* status
* items

OrderItem fields:

* id
* productId
* quantity
* price

Created:

* Order entity
* OrderItem entity
* OrderRepository
* OrderService
* OrderController

Order database table:

purchase_order

OrderItem database table:

order_item

Implemented Order APIs:

* Create order
* Get all orders
* Get order by ID
* Delete order

REST endpoint:

/orders

Learned:

* @OneToMany
* CascadeType.ALL
* Entity relationships
* LocalDateTime
* BigDecimal
* Handling PostgreSQL reserved words
* Using @Table(name = "...") for explicit table names

========================================
5. USER MODULE
==============

Created one User entity instead of separate
Customer and Admin entities.

User fields:

* id
* name
* email
* password
* role

Example roles:

CUSTOMER
ADMIN

Database table:

app_user

Created:

* User entity
* UserRepository
* UserService
* UserController

Implemented User APIs:

* Create user
* Get all users
* Get user by ID
* Delete user

REST endpoint:

/users

Learned:

* Representing different user types using a role field.
* Basic user CRUD operations.
* Designing a simple user model for future authentication.

========================================
6. POSTGRESQL DATABASE
======================

Used PostgreSQL as the application database.

Created database:

shopcartpro_db

Main tables created/used:

* product
* purchase_order
* order_item
* app_user

Learned:

* Connecting Spring Boot to PostgreSQL.
* Using JDBC connection URLs.
* Database credentials in application configuration.
* Hibernate/JPA creating and updating database tables.
* How Java entities map to database tables.

========================================
7. REST API TESTING
===================

Tested the APIs using HTTP requests.

Product API:

GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}

Order API:

GET    /orders
GET    /orders/{id}
POST   /orders
DELETE /orders/{id}

User API:

GET    /users
GET    /users/{id}
POST   /users
DELETE /users/{id}

Used JSON request bodies for POST and PUT operations.

Verified that data was created and retrieved
successfully from PostgreSQL.

========================================
8. MAVEN
========

Used the Maven Wrapper included with the project.

Run application:

.\mvnw.cmd spring-boot:run

Run tests:

.\mvnw.cmd test

Build project:

.\mvnw.cmd clean package

Build JAR without tests:

.\mvnw.cmd clean package -DskipTests

Created a Spring Boot JAR inside:

target/

========================================
9. GIT AND GITHUB
=================

Used Git for version control.

Used GitHub to store the project remotely.

Repository:

ShoppingCartPro-backend

Main Git commands learned:

git status
git add .
git commit
git push
git pull
git log
git remote -v

Typical workflow:

1. Make code changes.
2. Check git status.
3. Stage changes.
4. Commit changes.
5. Push to GitHub.

========================================
10. DOCKER
==========

Dockerized the Spring Boot backend.

Dockerfile was created using:

* Eclipse Temurin JDK
* Application JAR
* Working directory /app
* Port 8080
* Java JAR startup command

Docker flow:

Spring Boot code
↓
Maven build
↓
JAR file
↓
Docker image
↓
Docker container

========================================
11. DOCKER IMAGE
================

Created Docker image:

shopcartpro-backend_img

Learned:

* Dockerfile
* docker build
* docker images
* Image naming
* Docker image vs container

========================================
12. DOCKER CONTAINER
====================

Created backend container:

shopcartpro-backend_container

Container exposes:

8080:8080

Learned:

* docker run
* docker ps
* docker ps -a
* docker stop
* docker start
* docker restart
* docker logs
* docker rm
* docker rm -f

========================================
13. DOCKER + POSTGRESQL
=======================

The Spring Boot backend runs inside Docker.

PostgreSQL remains running on the Windows host machine.

Architecture:

Windows Host
│
├── PostgreSQL
│     └── shopcartpro_db
│
└── Docker
└── shopcartpro-backend_container
│
└── Spring Boot application

The container connects to the host PostgreSQL
using:

host.docker.internal

This allowed the backend to run inside Docker
while continuing to use the existing PostgreSQL
database on the host.

========================================
14. DOCKER VOLUMES
==================

Practiced Docker volumes using a separate
test volume.

Created:

shopcartpro-test-volume

Learned:

* What a Docker volume is.
* How to create a volume.
* How to inspect a volume.
* How to mount a volume into a container.
* How data survives container removal.
* How the same volume can be mounted by another container.
* How to remove a volume.

Important concept:

Container = application/runtime

Volume = persistent data

The Docker volume exercise was performed using
test data and did not modify the real PostgreSQL
database.

========================================
15. DOCKER CLEANUP
==================

Learned how to remove:

* Containers
* Images
* Volumes

Also learned the difference between:

docker rm

and

docker rm -f

and the importance of checking containers and
images before deleting them.

========================================
16. SPRING SECURITY
===================

Spring Security is the final major part planned
for ShopCartPro.

Security goals:

* Authenticate users.
* Protect API endpoints.
* Hash passwords.
* Use roles.
* Restrict admin-only operations.
* Allow authenticated customers to access
  appropriate operations.

Planned security concepts:

Authentication

* Verify who the user is.

Authorization

* Decide what the user is allowed to do.

PasswordEncoder

* Store passwords securely using hashing.
* Never store real passwords as plain text.

Roles:

CUSTOMER
ADMIN

Example security design:

PUBLIC

* Login
* Registration

CUSTOMER

* View products
* Create orders
* View appropriate order information

ADMIN

* Manage products
* Manage users
* Perform administrative operations

Spring Security concepts to learn:

* SecurityFilterChain
* PasswordEncoder
* AuthenticationManager
* UserDetailsService
* UserDetails
* Authorities / Roles
* HTTP security rules
* Authentication
* Authorization
* BCrypt password hashing

Important:

The current User module stores passwords as plain text
only as temporary development data.

Before the project is considered complete,
passwords should be stored using a PasswordEncoder,
such as BCrypt.

========================================
17. CURRENT PROJECT ARCHITECTURE
================================

ShopCartPro is intentionally a MONOLITH.

Current structure:

ShopCartPro
│
├── Product Module
│   ├── Entity
│   ├── Repository
│   ├── Service
│   └── Controller
│
├── Order Module
│   ├── Entity
│   ├── OrderItem
│   ├── Repository
│   ├── Service
│   └── Controller
│
├── User Module
│   ├── Entity
│   ├── Repository
│   ├── Service
│   └── Controller
│
├── PostgreSQL
│
├── Spring Security
│
└── Docker

========================================
18. IMPORTANT CONCEPTS LEARNED
==============================

Spring Boot:

* Application structure
* REST APIs
* Dependency Injection
* Controllers
* Services
* Repositories

JPA / Hibernate:

* Entities
* Primary keys
* Relationships
* CRUD operations
* Table mapping

PostgreSQL:

* Database
* Tables
* Data persistence
* SQL/database interaction

REST:

* GET
* POST
* PUT
* DELETE
* JSON
* HTTP endpoints

Git:

* Version control
* Commits
* Branches
* GitHub
* Push / Pull

Docker:

* Images
* Containers
* Dockerfile
* Ports
* Environment variables
* Volumes
* Container lifecycle

Security:

* Authentication
* Authorization
* Password hashing
* Roles
* Protected endpoints

========================================
19. PROJECT CHECKPOINTS
=======================

CHECKPOINT 1

* Spring Boot project created.
* PostgreSQL connected.

CHECKPOINT 2

* Product module completed.
* Product APIs tested.

CHECKPOINT 3

* Order module created.
* OrderItem created.
* Order APIs tested.

CHECKPOINT 4

* User module created.
* CUSTOMER and ADMIN roles introduced.
* User APIs tested.

CHECKPOINT 5

* Maven build completed.
* JAR created.

CHECKPOINT 6

* Dockerfile created.
* Backend Docker image created.
* Backend Docker container created.

CHECKPOINT 7

* Docker backend successfully connected
  to PostgreSQL running on the host.

CHECKPOINT 8

* Docker volume concepts practiced safely
  using a test volume.

CHECKPOINT 9

* Project changes committed and pushed to GitHub.

NEXT CHECKPOINT

* Connect Users with Orders.
* Implement Spring Security.
* Hash passwords.
* Implement authentication.
* Implement authorization using CUSTOMER
  and ADMIN roles.
* Protect appropriate API endpoints.

FINAL GOAL

* Complete ShopCartPro as a secure,
  containerized Spring Boot monolith.

========================================
20. PROJECT 2 - FUTURE MICROSERVICES PROJECT
============================================

Microservices are NOT being introduced into
ShopCartPro.

ShopCartPro is the learning project for:

* Spring Boot
* REST APIs
* JPA
* PostgreSQL
* Git/GitHub
* Docker
* Spring Security

A separate Project 2 will be created later
to learn:

* Microservices
* Multiple Spring Boot services
* Service-to-service communication
* Separate databases
* API Gateway
* Docker Compose
* Service discovery
* Containerized microservice architecture

This keeps ShopCartPro focused and prevents
mixing monolithic and microservices architecture
in the same learning project.
