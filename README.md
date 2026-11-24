# Energy Management System

Distributed Systems – Assignment 2 (Asynchronous Communication), UTCN

A web application for managing users and smart energy metering devices, built as containerized microservices behind a reverse proxy, with asynchronous communication through RabbitMQ.

- **Admin** – CRUD on users and devices, assigns devices to users
- **Client** – sees their devices and their hourly energy consumption for a chosen day

## Architecture

| Component | Technology | Responsibility |
|---|---|---|
| frontend | React + Vite + Material UI, served by nginx | Login, role-based pages, consumption chart |
| traefik | Traefik v3 | Reverse proxy / API gateway, JWT validation via ForwardAuth |
| auth-service | Spring Boot | Register, login, JWT issuing and validation |
| user-service | Spring Boot | CRUD on user profiles, publishes user events |
| device-service | Spring Boot | CRUD on devices, device-to-user assignment, publishes device events |
| monitoring-service | Spring Boot | Consumes measurements, computes hourly consumption |
| rabbitmq | RabbitMQ 4 | Message broker for synchronization events and measurements |
| simulator | Python (runs outside Docker) | Generates smart meter readings every 10 minutes |
| auth-db, user-db, device-db, monitoring-db | PostgreSQL 16 | One database per service |

All HTTP requests go through Traefik on port 80. Requests to `/api/users`, `/api/devices` and `/api/monitoring` are validated by auth-service before being forwarded.

## Messaging

- **Synchronization (publish/subscribe):** user-service and device-service publish events to the topic exchange `sync.exchange` with routing keys such as `user.created`, `user.deleted`, `device.created`, `device.updated`, `device.deleted`. Each interested service has its own queue:

  | Queue | Binding | Consumer |
    |---|---|---|
  | `device-service.sync` | `user.*` | device-service |
  | `auth.sync` | `user.deleted` | auth-service |
  | `monitoring.sync` | `device.*` | monitoring-service |

- **Measurements (point-to-point):** the simulator sends readings to the `measurements` queue. monitoring-service consumes them, prints them to the console and adds them to the hourly total of each device.

  ```json
  {"timestamp": "2026-11-20T18:10:00", "device_id": "<device uuid>", "measurement_value": 0.231}
  ```

## Prerequisites

- Docker Desktop (with Docker Compose)
- Python 3 (for the simulator)

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
docker compose down -v     # also deletes the databases and queues
```

## Device data simulator

The simulator runs on the host and connects to RabbitMQ on `localhost:5672`.

```bash
cd simulator
pip install -r requirements.txt
python simulator.py
```

Settings in `simulator/config.json`:

| Setting | Meaning |
|---|---|
| `devices` | ids of the devices to simulate (must exist in the system) |
| `interval_minutes` | simulated time between readings (10) |
| `seconds_between_readings` | real time between readings; lower values speed up the simulation, `0` sends immediately |
| `start` | simulated start date and time |
| `duration_hours` | how much simulated time to generate |


To see the chart: assign a simulated device to a client, run the simulator for a day, log in as that client and open **My consumption**.

## Access

| What | URL |
|---|---|
| Application | http://localhost |
| Traefik dashboard | http://localhost:8080 |
| RabbitMQ management UI | http://localhost:15672 (guest / guest) |
| Swagger – user-service | http://localhost:8081/swagger-ui.html |
| Swagger – device-service | http://localhost:8082/swagger-ui.html |
| Swagger – auth-service | http://localhost:8083/swagger-ui.html |
| Swagger – monitoring-service | http://localhost:8084/swagger-ui.html |

## Default admin account

Created automatically on first start:

- **username:** `admin`
- **password:** `admin123`

## Project structure

```
├── auth-service/              Spring Boot authentication service
├── user-service/              Spring Boot user management service
├── device-service/            Spring Boot device management service
├── monitoring-service/        Spring Boot monitoring service (RabbitMQ consumer)
├── simulator/                 Python device data simulator
├── frontend/                  React application
├── db_dumps/                  SQL dumps of each database
├── deployment-diagram-a1.drawio  UML deployment diagram from 1st assignment
├── deployment-diagram-a2.drawio  UML deployment diagram from 2nd assignment
└── docker-compose.yaml        Deployment of all services, databases, RabbitMQ and Traefik
```