# RBAC-Backend

# Role-Based Access Control (RBAC) API

This project implements a Role-Based Access Control (RBAC) system using Spring Boot and JWT (JSON Web Tokens) for authentication and authorization. It provides endpoints for user registration, login, and accessing role-protected resources.

## Features

- **User Registration**: Allows users to register with a username, password, and roles.
- **Authentication**: JWT-based authentication to secure API endpoints.
- **Authorization**: Role-based access control to restrict access to specific endpoints.
- **JWT Token Management**: Issue, validate, and parse JWT tokens.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Security**: Spring Security, JWT
- **Database**: H2 (in-memory) or any other configured database
- **Build Tool**: Maven

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/rbac-api.git
   cd rbac-api
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
    - Base URL: `http://localhost:8080`

## Endpoints

### Authentication Endpoints

#### 1. **Register User**
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Description**: Register a new user with a username, password, and roles.
- **Body**:
  ```json
  {
    "username": "newuser",
    "password": "password123",
    "roles": ["USER"]
  }
  ```
- **Response**:
  ```json
  {
    "message": "User registered successfully"
  }
  ```

#### 2. **Login**
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Description**: Authenticate the user and get a JWT token.
- **Body**:
  ```json
  {
    "username": "newuser",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "token": "jwt_token_string"
  }
  ```

### Protected Endpoints

#### 1. **Access Role-Protected Resource**
- **URL**: `/api/resource`
- **Method**: `GET`
- **Headers**:
  ```json
  {
    "Authorization": "Bearer <jwt_token_string>"
  }
  ```
- **Response**:
  ```json
  {
    "message": "Access granted to protected resource"
  }
  ```

## Project Structure

```
src/main/java/com/example/rbac
│
├── Controllers       # REST controllers for handling API endpoints
│   └── AuthController.java
│
├── Models            # Entity classes
│   ├── User.java
│   ├── Role.java
│   └── RoleEnum.java
│
├── Repositories      # JPA repositories for database operations
│   ├── UserRepository.java
│   └── RoleRepository.java
│
├── Services          # Business logic and service layer
│   └── AuthService.java
│
├── Utils             # Utility classes (e.g., JWT handling)
│   └── JwtUtils.java
│
└── Security          # Spring Security configuration
    ├── SecurityConfig.java
    └── JwtAuthenticationFilter.java
```

## Running Tests

Use Postman or any API testing tool to test the endpoints.

### Testing Scenarios

1. **Register a User**:
    - Register a user with roles like `["USER", "ADMIN"]`.

2. **Login**:
    - Login using the registered credentials to get a JWT token.

3. **Access Protected Resource**:
    - Use the token in the `Authorization` header to access a protected resource.

## Common Issues

1. **403 Forbidden**:
    - Ensure the endpoint is correctly configured in `SecurityConfig.java`.
    - Verify the JWT token and its validity.

2. **Token Not Working**:
    - Ensure the JWT is passed in the `Authorization` header as `Bearer <token>`.

3. **Endpoint Not Accessible**:
    - Check `@RequestMapping` annotations and endpoint configurations.



