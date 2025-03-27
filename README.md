# Car Management System

This is a multi-module Java application that follows a clean 3-layered architecture (DAO, service, controller/view). It provides full support for car data management across multiple user interfaces and deployment types.

The system includes:
- a backend module with core business logic and data access,
- a desktop GUI module built with Java Swing,
- a servlet-based web module with both Thymeleaf and JSON API support,
- a standalone Spring Boot module with JPA integration and REST endpoints.

A separate React frontend consumes the REST API provided by the Spring module.

---

## Modules

### 1. Backend Module

Provides the core logic and data access layer for cars.

- Entity classes: `CarModel`, `BaseEntity`
- DAO interface: `CarDao`, with two implementations:
    - `CarDaoJdbc` (using JDBC and MySQL)
    - `CarMemoryDB` (in-memory version)
- Factory pattern to select DAO implementation based on profile
- Service layer for validation and logic
- Profile-based configuration loaded from JSON (`application<profile>.json`)

Used by both the desktop and servlet-based web modules.

---

### 2. Desktop Module

A standalone Java Swing graphical interface for managing cars.

- Add, update, delete cars via dialogs
- Search by ID
- List all cars
- Connects directly to the backend service layer

Intended for local desktop use or quick testing.

---

### 3. Web Module (Servlet-based)

Provides two interfaces, both deployed as a WAR to a Tomcat server.

#### 3.1 Thymeleaf Servlet

- URL: `/carModelsThyme`
- Template rendering using Thymeleaf (`index.html`, `login.html`)
- Session-based authentication (`/login`, `/logout`)
- HTTP filter for access control

#### 3.2 JSON API Servlet

- URL: `/carModels`
- Methods supported: `GET`, `POST`, `PUT`, `DELETE`
- JSON request/response format using Jackson
- Error messages with appropriate HTTP status codes

#### Deployment

This module is packaged using the `war` Gradle plugin and deployed to a Tomcat server using Gradle tasks:

```bash
./gradlew :web:deploy
./gradlew :web:undeploy
```

Tomcat location must be configured using the `CATALINA_HOME` environment variable.

---

### 4. Spring Boot Module (REST API with JPA)

A self-contained Spring Boot application that manages car data and related extras.

- Full REST support via annotated controllers
- 3-layered architecture (controller → service → repository)
- Spring Data JPA for persistence
- MySQL backend with configurable connection via `application.properties`
- Profile support (`jpa`, `memory`)
- DTO-based API using MapStruct

#### Additional Entity: `CarExtra`

- Related to `CarModel` (one-to-many relationship)
- Only available in the Spring module
- Used to store additional details about cars

#### Exposed Endpoints

- `/cars` (GET, POST)
- `/cars/{id}` (GET, PUT, DELETE)
- `/cars?year=YYYY` – filter cars by year
- `/cars/{carId}/extras` (GET, POST)
- `/cars/{carId}/extras/{extraId}` (DELETE)

---

## Frontend for Spring Module

A React-based frontend application connects to all Spring REST endpoints.

- Lists and manages cars
- Handles car extras (create, view, delete)
- Communicates using `axios`
- Uses form validation and modern UI patterns

> The React frontend source code is available here:  
> [https://github.com/Marci517/car-management-system-react](https://github.com/Marci517/car-management-system-react)

---

## Technologies Used

### Shared Across Modules
- Java 17
- Gradle
- SLF4J + Logback
- Jackson (JSON serialization/deserialization)
- HikariCP (JDBC connection pool)
- MySQL database
- Jakarta Servlet API
- Lombok (annotations)
- MapStruct (DTO mapping)

### Spring Boot Module
- Spring Boot 3.4.0
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- Hibernate ORM
- Entity: `CarModel`, `CarExtra`

### Web Module (Servlet-based)
- Deployed as WAR to Tomcat
- Thymeleaf template engine
- Jackson-based JSON API

### Desktop Module
- Java Swing GUI
- Direct connection to backend service

---

> Additional Spring-specific features such as pagination, caching, and advanced filtering are available in the `springFeatures` branch:

> [https://github.com/Marci517/car-management-system-spring/tree/springFeatures](https://github.com/Marci517/car-management-system-spring/tree/springFeatures)

