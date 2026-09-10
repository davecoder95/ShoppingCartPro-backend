# ShopCartPro – JWT Security

## 1. JWT Authentication

ShopCartPro uses **JSON Web Token (JWT)** authentication.

JWT is used after the user successfully logs in.

---

## 2. Login Flow

The user sends their email and password:

```http
POST /auth/login
```

Example:

```json
{
    "email": "harry2@example.com",
    "password": "test123"
}
```

Authentication flow:

```text
Login Request
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
     ↓
Authentication successful
     ↓
JwtService
     ↓
JWT generated
     ↓
JWT returned to client
```

---

## 3. JWT Generation

`JwtService` creates the JWT after successful authentication.

The token contains information such as:

* Subject → user email
* Role
* Issued time
* Expiration time

The JWT is signed using a secret key.

Example:

```text
email + role + issuedAt + expiration
              ↓
           signature
              ↓
             JWT
```

Every successful login generates a new JWT.

---

## 4. Sending the JWT

After login, the client sends the JWT with protected requests.

Example:

```http
GET /orders
Authorization: Bearer <JWT>
```

The password is **not** sent again.

---

## 5. JWT Authentication Filter

Created:

```text
config/JwtAuthenticationFilter.java
```

The filter extends:

```java
OncePerRequestFilter
```

It runs for incoming HTTP requests.

Flow:

```text
HTTP Request
     ↓
Read Authorization header
     ↓
Check "Bearer "
     ↓
Extract JWT
     ↓
Validate JWT
     ↓
Extract user information
     ↓
Create Authentication
     ↓
SecurityContext
     ↓
Controller
```

The filter is registered before Spring Security's username/password filter:

```java
.addFilterBefore(
    jwtAuthenticationFilter,
    UsernamePasswordAuthenticationFilter.class
)
```

---

## 6. Stateless Authentication

Spring Security is configured with:

```java
.sessionManagement(session ->
    session.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS
    )
)
```

This means Spring Security does not maintain an HTTP login session.

The client sends the JWT with each protected request.

```text
Request 1 → JWT
Request 2 → JWT
Request 3 → JWT
```

Instead of:

```text
Login
  ↓
Server Session
  ↓
Session ID
  ↓
Future Requests
```

---

## 7. JWT vs Session

### Traditional Session Authentication

```text
Login
 ↓
Server creates session
 ↓
Session stored on server
 ↓
Client receives session ID
 ↓
Client sends session ID
```

### JWT Authentication

```text
Login
 ↓
Server creates JWT
 ↓
Client receives JWT
 ↓
Client sends JWT with each request
 ↓
Server validates JWT
```

JWT authentication is stateless because the authentication information is carried by the token instead of a server-side HTTP session.

---

## 8. Database Usage

### During Login

The database is used.

```text
Email + Password
      ↓
Database
      ↓
Find User
      ↓
BCrypt password verification
```

### During JWT Request

JWT validation itself does not inherently require a database lookup.

The server can validate:

* JWT signature
* JWT expiration
* JWT claims

using the JWT and secret key.

The current implementation may load the user through `CustomUserDetailsService` so that Spring Security can obtain the user's `UserDetails` and authorities.

---

## 9. Protected Endpoints

Current security configuration:

```text
/auth/**       → Public
/products/**   → Public
/users/**      → Public
/orders/**     → Authenticated
Everything else → Authenticated
```

Example:

```http
GET /orders
Authorization: Bearer <JWT>
```

Without a valid JWT:

```text
401 Unauthorized
```

With a valid JWT:

```text
200 OK
```

---

## 10. JWT Authentication Flow

Complete flow:

```text
             LOGIN
               ↓
       Email + Password
               ↓
      Spring Security
               ↓
      Database + BCrypt
               ↓
        Authentication
               ↓
          JwtService
               ↓
          Create JWT
               ↓
       Return JWT to Client
               ↓
               ↓
      FUTURE REQUEST
               ↓
       Bearer JWT
               ↓
   JwtAuthenticationFilter
               ↓
       Validate JWT
               ↓
      SecurityContext
               ↓
        Authentication
               ↓
          Controller
```

---

## 11. Current Status

### Completed

✅ Spring Security
✅ BCrypt password hashing
✅ UserDetailsService
✅ AuthenticationManager
✅ AuthenticationProvider
✅ `/auth/login`
✅ JWT generation
✅ JWT validation
✅ JWT authentication filter
✅ Bearer token authentication
✅ Stateless authentication
✅ Protected `/orders` endpoint

### Next Improvements

* Move JWT secret from Java code into configuration/environment variables
* Implement role-based authorization
* Prevent users from registering themselves as `ADMIN`
* Add validation and exception handling
* Improve DTO usage
* Rebuild and test the Docker image
