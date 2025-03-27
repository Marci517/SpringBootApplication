## springFeatures Branch Overview

This branch extends the functionality of the main branch by introducing additional Spring-based features such as pagination, caching, dynamic filtering, interceptors, and scheduled tasks. These features are aimed at improving the performance, maintainability, and scalability of the application.

> All changes described below apply specifically to the `spring` module of the project.  
> These features are only active when the application is run with the `spring` profile.  
> The `memory` profile is not affected by these additions.

### 1. Pagination and Sorting

REST endpoints that return collections now support pagination and sorting. Instead of returning all elements, endpoints now return a limited number of items per page.

- Supported query parameters:
  - `page`: which page to retrieve (default: 0)
  - `size`: number of items per page (default: 10)
  - `sort`: the field to sort by (e.g., `createdAt`)
  - `direction`: sort direction, either `asc` or `desc` (default: `asc`)
- The total number of matching items is included in the `X-Total-Count` response header so that the client knows how many pages are available.

### 2. Spring Caching with Redis

Caching is implemented for one selected entity to reduce database load on repeated GET requests.

- GET requests cache the JSON response in Redis.
- Modifying operations (POST, PUT, DELETE) evict affected cache entries to ensure consistency.
- Cache keys are carefully constructed to prevent collisions.
- Redis is used as the caching backend instead of local memory.
- This feature is available only under the `spring` profile and requires Redis to be running.

### 3. Dynamic Filtering using Spring Specification and Criteria API

Advanced filtering is supported for collection-based endpoints through the Spring Specification and Criteria Query APIs.

- A filter POJO is used to capture optional query parameters such as minimum/maximum values or date ranges.
- These filters are dynamically combined and translated into JPA queries.
- Only non-null filtering parameters are applied.
- Optionally, external query construction tools like Querydsl can also be used.

### 4. HTTP Request Interceptors

Two interceptors are implemented using Spring's `HandlerInterceptor` interface:

1. Logging interceptor: logs HTTP method, URL, and response status for every request.
2. Middleware interceptor: can be applied selectively to endpoints to enforce general conditions or authorization checks.

### 5. Scheduled Maintenance Tasks

The application includes support for background tasks that run on a schedule, independent of HTTP calls.

- Maintenance tasks are implemented:
  - Generating statistical summaries and periodic reports
- Tasks are implemented using the `@Scheduled` annotation in Spring.

---

#### This branch serves as an extended version of the base application, demonstrating the integration of advanced Spring features following industry best practices.
All additions are located in the `spring` module and are only activated with the `spring` profile.
