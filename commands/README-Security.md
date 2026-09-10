# ShopCartPro – Security


User module checkpoint
✅ User entity
✅ UserRepository
✅ UserService
✅ UserController
✅ UserRequestDTO
✅ UserResponseDTO
✅ UserMapper
✅ BCrypt password hashing
✅ Database authentication
⏸️ Role authorization — later


## 1. Spring Security

Added Spring Security using:

```xml
spring-boot-starter-security
```

Spring Security protects endpoints and handles authentication/authorization.

---

## 2. Security Configuration

Created:

```text
config/SecurityConfig.java
```

Current rules:

* `/products/**` → public
* `/users/**` → public
* `/orders/**` → authenticated users
* Everything else → authenticated

CSRF is disabled because this is currently a REST API.

HTTP Basic authentication is enabled for testing with Postman.

---

## 3. Password Encryption

Added a `PasswordEncoder` bean using:

```java
BCryptPasswordEncoder
```

When a new user is created:

```java
user.setPassword(passwordEncoder.encode(user.getPassword()));
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

---

## 5. Authentication Flow

```text
Postman
   ↓
Spring Security
   ↓
CustomUserDetailsService
   ↓
PostgreSQL
   ↓
BCrypt password verification
   ↓
Authenticated / 401 Unauthorized
```

### Current Status

✅ Spring Security added
✅ BCrypt password hashing
✅ Database user authentication
✅ HTTP Basic authentication
✅ `/orders/**` requires authentication

### Next

Implement **role-based authorization** so that `ADMIN` and `CUSTOMER` have different permissions.
