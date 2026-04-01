
# Gym and Wellness Management System

A robust backend solution designed to streamline gym operations, member health tracking, and facility management. Built with Spring Boot 3.5 and PostgreSQL, this platform manages the intersection of fitness scheduling and data-driven health insights.

## Core Functionalities

### User Management and Security
* Role-Based Access: Dedicated workflows for Administrators, Trainers, and Members.
* Identity Management: Secure authentication and authorization powered by JWT (JSON Web Tokens).

### Asset and Facility Management
* Inventory Tracking: Manage gym equipment across multiple rooms and locations.
* Maintenance Lifecycle: Log equipment issues, track repair statuses (Operational, Under Repair, Out of Service), and manage maintenance history.

### Dynamic Scheduling
* Class Coordination: Manage fitness classes, personal sessions, and trainer assignments.
* Conflict Prevention: Intelligent logic to prevent double-booking of rooms or equipment during specific time slots.

### Health Metrics and Analytics
* Member Progress: Track vital health metrics and fitness data over time.
* Data Visualization: Backend support for generating personalized health insights and progress reports.

## Technology Stack

* Language: Java 21
* Framework: Spring Boot 3.5
* Database: PostgreSQL 16
* Security: Spring Security + JWT
* Persistence: Spring Data JPA (Hibernate)
* DevOps: Docker & Docker Compose
* Build Tool: Maven

## Project Structure

```text
├── gym/                  # Spring Boot application source
│   ├── src/main/java     # Logic, Entities, and Controllers
│   ├── src/main/resources # Application properties and SQL migrations
│   └── pom.xml           # Project dependencies
├── postgres-docker/      # Containerized Database Environment
│   ├── docker-compose.yml # Postgres configuration
│   └── init.sql          # Schema and initial seed data
└── README.md
```

## Setup and Installation

### 1. Prerequisites
* Docker Desktop installed and running.
* Java 21 JDK and Maven installed.

### 2. Launch the Database
Navigate to the docker directory and start the PostgreSQL instance:
```bash
cd postgres-docker
docker-compose up -d
```
The database will initialize automatically using the scripts provided in the init folder.

### 3. Application Configuration
To keep the project secure, avoid hardcoding credentials. Set the following environment variables or update your local profile:
* DB_URL: The JDBC connection string for your instance.
* DB_USERNAME: Your database administrative user.
* DB_PASSWORD: Your database password.

### 4. Run the Application
Navigate to the root directory and start the Spring Boot server:
```bash
mvn clean spring-boot:run
```
The API will be accessible at http://localhost:8080.

## Testing
Run the automated test suite to ensure system integrity:
```bash
mvn test
```

## License
This project is developed for demonstration and educational purposes.