# Java AWS 3-Tier Application (End-to-End)

## Project Summary

This project is a fully working, Dockerized **3-Tier Web Application** built completely from scratch.

It demonstrates:
- Frontend–backend–database integration  
- Real-world debugging and troubleshooting  
- Containerization and orchestration  
- Production-style best practices  

The application allows users to **add and view employee details** via a web interface.

This project closely mirrors how applications are designed, built, and deployed in **enterprise and cloud (AWS-style) environments**.

---

## 3-Tier Architecture Overview

Browser
|
v
Frontend Tier
(Nginx + HTML + JavaScript)
|
v
Application Tier
(Java Servlets + Tomcat 10)
|
v
Data Tier
(MySQL 8)


Each tier runs in its **own Docker container** and communicates over a **Docker bridge network**.

---

## Final Project Structure

java-aws-3tier-app/

│

├── backend/

│ ├── Dockerfile

│ ├── pom.xml

│ ├── wait-for-mysql.sh

│ └── src/main/

│ ├── java/com/app/

│ │ ├── HealthServlet.java

│ │ ├── UserServlet.java

│ │ └── EmployeeServlet.java

│ └── webapp/WEB-INF/

│ └── web.xml

│

├── frontend/

│ ├── Dockerfile

│ ├── nginx.conf

│ └── html/

│ └── index.html

│

├── db/

│ └── schema.sql

│

├── docker-compose.yml

├── .env.example

├── .gitignore

└── README.md


---

## Technologies Used

- Java 17  
- Apache Tomcat 10 (Jakarta Servlet API)  
- MySQL 8  
- Maven  
- Docker & Docker Compose  
- Nginx  
- Ubuntu (WSL)

---

## Development Journey (From Scratch to End)

### Backend Development (Java + Tomcat)

- Created a Maven-based Java web application
- Used **Jakarta Servlet API** (required for Tomcat 10)
- Implemented servlets:
  - `HealthServlet` – health check
  - `UserServlet` – basic DB connectivity test
  - `EmployeeServlet` – core business logic (GET + POST)
- Configured servlet mappings using `web.xml` (Jakarta 5.0)

**Key Learning**
- Tomcat 10 does **not** support `javax.servlet`
- All imports must use `jakarta.servlet`

---

### Database Setup (MySQL)

- Used MySQL 8 running in a Docker container
- Initialized schema using `schema.sql`
- Designed `employees` table:

```sql
CREATE TABLE employees (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  department VARCHAR(50)
);
```

Created a dedicated database user (appuser)

Followed the least-privilege security principle

## Frontend Development (HTML + JavaScript)

- Built a simple HTML user interface served via **Nginx**
- Added a form to insert employee details
- Used JavaScript `fetch()` API to:
  - **POST** employee data
  - **GET** employee list
- Parsed backend JSON responses
- Dynamically rendered employee data in an HTML table

### Key Learning
- `fetch()` requires the correct `Content-Type` header
- Missing headers cause servlet request parameters to be `null`

---

## Containerization with Docker

- Created separate Dockerfiles for:
  - **Backend** (Tomcat + WAR deployment)
  - **Frontend** (Nginx + static files)
- Used environment variables for database configuration
- Ensured no credentials were hardcoded in Java source code

---

## Docker Compose Orchestration

- Orchestrated services using `docker-compose.yml`:
  - MySQL
  - Backend
  - Frontend
- Created a custom Docker bridge network
- Enabled container-to-container communication using service names
- Used `.env` file for configuration (excluded from GitHub)

---

##  Debugging & Real-World Issues Solved

This project involved real production-style debugging, including:

- Maven dependency resolution issues
- Tomcat 10 servlet mapping failures
- `javax` → `jakarta` migration errors
- Docker build cache corruption
- MySQL authentication and privilege errors
- Frontend form data arriving as `null`
- Nginx static file caching issues
- MySQL initialization scripts running only once

Each issue was diagnosed and resolved step-by-step.

---

## Environment Configuration

### `.env.example`

DB_HOST=mysql-db
DB_PORT=3306
DB_NAME=appdb
DB_USER=your_db_user
DB_PASSWORD=your_db_password


The `.env` file is intentionally excluded from GitHub for security reasons.

---

## Application Endpoints

### Backend APIs

| Endpoint           | Method | Description             |
|--------------------|--------|-------------------------|
| `/api/health`      | GET    | Health check            |
| `/api/users`       | GET    | Test DB connection      |
| `/api/employees`   | GET    | Fetch all employees     |
| `/api/employees`   | POST   | Add new employee        |

---

## How to Run the Project

### Clone Repository

```bash
git clone https://github.com/your-username/java-aws-3tier-app.git
cd java-aws-3tier-app

```

## Create `.env` File

Create the environment configuration file from the example:

```bash
cp .env.example .env

```

## Build & Start Containers

Build the Docker images and start all services using Docker Compose:

```bash
docker compose up --build -d

```

## Access the Application

### Frontend UI

http://localhost:8081


### Backend Health Check

http://localhost:9090/api/health


## Verify Database Data

### Connect to the MySQL Container

```bash
docker exec -it mysql-db mysql -uroot -p appdb

```

### Verify Stored Employee Records

```bash

SELECT * FROM employees;

```

## Key Concepts Demonstrated

- **3-Tier Architecture Design**  
  Clear separation of frontend, application, and database layers for scalability and maintainability.

- **Jakarta Servlet API**  
  Built using modern Jakarta namespaces compatible with Apache Tomcat 10.

- **Frontend–Backend Communication**  
  REST-style communication using HTTP methods and JSON payloads.

- **Environment-Based Configuration**  
  Application configuration managed via environment variables using `.env` files.

- **Docker Networking**  
  Service-to-service communication enabled through Docker bridge networks and container DNS.

- **Database Security Best Practices**  
  Use of dedicated database users and least-privilege access control.

- **Real-World Debugging and Troubleshooting**  
  Hands-on resolution of dependency, containerization, networking, and runtime issues.


