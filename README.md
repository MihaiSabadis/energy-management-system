# Energy Management System

Distributed Systems – Assignment 1 (Request-Reply Communication), UTCN

A web application for managing users and smart energy metering devices, with containerized microservices behind a reverse proxy.

- **Admin** – CRUD on users and devices, assigns devices to users
- **Client** – logs in and sees the devices assigned to them

## Architecture

| Component | Technology | Responsibility |
|---|---|---|
| frontend | React + Vite + Material UI, served by nginx | Login and role-based pages |
| traefik | Traefik v3 | Reverse proxy / API gateway, JWT validation via ForwardAuth |
| auth-service | Spring Boot | Register, login, JWT issuing and validation |
| user-service | Spring Boot | CRUD on user profiles |
| device-service | Spring Boot | CRUD on devices, device-to-user assignment |
| auth-db, user-db, device-db | PostgreSQL 16 | One database per service |

All requests go through Traefik on port 80. Requests to `/api/users` and `/api/devices` are validated by auth-service before being forwarded.

## Prerequisites

- Docker Desktop (with Docker Compose)

## Build and run

From the project root:

```bash
docker compose up -d --build
```

Check that all containers are running:

```bash
docker compose ps
```

Stop the system:

```bash
docker compose down        # keeps the data
docker compose down -v     # also deletes the databases
```

## Access

| What | URL |
|---|---|
| Application | http://localhost |
| Traefik dashboard | http://localhost:8080 |
| Swagger – user-service | http://localhost:8081/swagger-ui.html |
| Swagger – device-service | http://localhost:8082/swagger-ui.html |
| Swagger – auth-service | http://localhost:8083/swagger-ui.html |

## Default admin account

Created automatically on first start:

- **username:** `admin`
- **password:** `admin123`

## Project structure

```
├── auth-service/        Spring Boot authentication service
├── user-service/        Spring Boot user management service
├── device-service/      Spring Boot device management service
├── frontend/            React application
└── docker-compose.yml   Deployment of all services, databases and Traefik
```
