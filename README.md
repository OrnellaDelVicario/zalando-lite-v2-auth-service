# Auth Service

## Description

The `Auth Service` is a core microservice within the Zalando Lite v2 platform, responsible for handling user authentication and authorization. It provides secure endpoints for user registration, login, and access token validation. By using **JSON Web Tokens (JWT)**, it ensures that other microservices can securely verify user identity without needing to communicate directly with the authentication service for every request. This design promotes a stateless and scalable architecture.

The service's responsibilities include:
-   **User registration**: Creating new user accounts with encrypted passwords.
-   **User login**: Authenticating users and generating a unique **JWT** upon successful login.
-   **Token validation**: Exposing an endpoint for other services to validate a given JWT, confirming the user's identity and permissions.

## Technologies

-   **Language**: Java 17
-   **Framework**: Spring Boot
-   **Database**: PostgreSQL for secure user data storage
-   **Security**: Spring Security for robust authentication and password hashing
-   **Token Management**: JSON Web Tokens (JWT) for secure, stateless authorization
-   **Containerization**: Docker

## API Endpoints

| Method | URL | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Creates a new user account. Expects a JSON payload with a username and password. |
| `POST` | `/api/auth/login` | Authenticates a user. Returns a **JWT** upon successful login. |
| `POST` | `/api/auth/validate`| Validates a given JWT. Other microservices use this to check if a user is authenticated. |

## How to Run

To run this service, you need to have Docker and Docker Compose installed.

1.  Navigate to the `auth-service` project's root directory.
2.  Run the following command to build the Docker image and start the service along with its PostgreSQL database:
    ```bash
    docker-compose up --build
    ```
3.  The service will be accessible at port `9080` (or the port specified in your `.env` file).