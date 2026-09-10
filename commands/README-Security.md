# ShopCartPro – Security

## User Module Checkpoint

✅ User entity
✅ UserRepository
✅ UserService
✅ UserController
✅ UserRequestDTO
✅ UserResponseDTO
✅ UserMapper
✅ BCrypt password hashing
✅ Database authentication
✅ JWT authentication
✅ Stateless session management
✅ Externalized JWT secret
⏸️ Role authorization — later

---

## 1. Spring Security

Added Spring Security using:

```xml
spring-boot-starter-security
```

Spring Security protects endpoints and handles authentication and authorization.

---

## 2. Security Configuration

Created:

```text
config/SecurityConfig.java
```

Current rules:

* `/auth/**` → public
* `/products/**` → public
* `/users/**` → public
* `/orders/**` → authenticated users
* Everything else → authenticated

CSRF is disabled because this is currently a REST API.

The application uses **stateless authentication** with JWT. Spring Security does not maintain an HTTP login session.

---

## 3. Password Encryption

Added a `PasswordEncoder` bean using:

```java
BCryptPasswordEncoder
```

When a new user is created:

```java
user.setPassword(
    passwordEncoder.encode(user.getPassword())
);
```

The plain-text password is **not stored** in the database.

Example:

```text
test123
   ↓
BCrypt
   ↓
$2a$10$........
```

When logging in, the user still enters the original password.

---

## 4. UserDetailsService

Created:

```text
service/CustomUserDetailsService.java
```

It loads a user from the database using the user's email.

Spring Security then uses:

* email → username
* BCrypt password → password verification
* role → authorization information

The role is configured with:

```java
.roles(user.getRole())
```

For example:

```text
CUSTOMER → ROLE_CUSTOMER
ADMIN    → ROLE_ADMIN
```

Role-based authorization is not implemented yet.

---

## 5. AuthenticationManager

Created an `AuthenticationManager` bean using Spring Security's `AuthenticationConfiguration`.

The login request is authenticated through:

```text
AuthController
      ↓
AuthenticationManager
      ↓
AuthenticationProvider
      ↓
CustomUserDetailsService
      ↓
UserRepository
      ↓
PostgreSQL
      ↓
BCrypt password verification
```

If authentication succeeds, the application generates a JWT.

---

## 6. JWT Authentication

Created:

```text
service/JwtService.java
```

Login endpoint:

```http
POST /auth/login
```

The user sends:

```json
{
    "email": "harry2@example.com",
    "password": "test123"
}
```

After successful authentication, the server creates a JWT containing information such as:

* user email
* role
* issued time
* expiration time

The JWT is returned to the client.

Subsequent protected requests use:

```http
Authorization: Bearer <JWT>
```

---

## 7. JWT Authentication Filter

Created:

```text
config/JwtAuthenticationFilter.java
```

The filter checks incoming requests for a Bearer token.

Flow:

```text
HTTP Request
     ↓
Authorization: Bearer <JWT>
     ↓
JwtAuthenticationFilter
     ↓
Validate JWT
     ↓
Extract user information
     ↓
SecurityContext
     ↓
Authenticated Request
```

The filter is registered before Spring Security's username/password authentication filter.

---

## 8. Stateless Authentication

Configured:

```java
.sessionManagement(session ->
    session.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS
    )
)
```

This means Spring Security does not maintain an HTTP login session.

The client sends the JWT with each protected request:

```text
Request 1 → JWT
Request 2 → JWT
Request 3 → JWT
```

The server validates the JWT for each request.

---

## 9. Externalized JWT Secret

The JWT signing secret is not hard-coded inside `JwtService`.

It is configured in:

```text
application.properties
```

using:

```properties
jwt.secret=...
```

`JwtService` reads the secret from application configuration and uses it to sign and verify JWTs.

This keeps the secret separate from the Java source code and allows it to later be supplied through environment variables, including Docker environment variables.

---

## 10. Authentication Flow

```text
                    LOGIN

Postman
   ↓
POST /auth/login
   ↓
Email + Password
   ↓
AuthenticationManager
   ↓
AuthenticationProvider
   ↓
CustomUserDetailsService
   ↓
PostgreSQL
   ↓
BCrypt Verification
   ↓
Authentication Successful
   ↓
JwtService
   ↓
JWT Generated
   ↓
JWT Returned
```

For a protected request:

```text
                 PROTECTED REQUEST

Client
   ↓
Bearer JWT
   ↓
JwtAuthenticationFilter
   ↓
JWT Validation
   ↓
SecurityContext
   ↓
Authenticated
   ↓
OrderController
   ↓
200 OK
```

Without a valid JWT:

```text
401 Unauthorized
```

---

## Current Status

✅ Spring Security
✅ BCrypt password hashing
✅ Database user authentication
✅ JWT login
✅ JWT generation
✅ JWT validation
✅ JWT authentication filter
✅ Bearer token authentication
✅ Stateless authentication
✅ Externalized JWT secret
✅ `/orders/**` requires authentication

## Next

Implement **role-based authorization** so that `ADMIN` and `CUSTOMER` have different permissions.
