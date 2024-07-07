# REST API for a note taking app

## Simple Web Application for managing notes by categories

#### The technologies behind it:
- Java, Spring Boot (Web, Data JPA, Security)
- Docker
- JSON Web Token
- Thymeleaf
- Validation
- PostgreSQL
- Flyway
- Lombok
- Swagger

---

#### Both REST and Front-end solutions are implemented:
- `/api/V1/**` for Thymeleaf-based implementation.
- `/api/V2/**` for accessing the REST endpoints.
- `/api/*/auth/login` when using the project for the first time.

---

### How to run it?

#### The entire application can be run with a single command on a terminal:
```
$ docker compose up -d
```

#### If you want to stop it, use the following command:
```
$ docker compose down
```
