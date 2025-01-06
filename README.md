# Car Rental App - Backend

## Overview
The Car Rental App backend is a Spring Boot application that provides RESTful APIs for managing cars, users, and rental services. It handles operations such as car registration, updates, deletions, and user authentication. The backend also manages photo uploads for car listings and ensures data integrity through exception handling and transactional operations.

## Features
- Car management (CRUD operations)
- User registration and authentication
- Photo upload and attachment to cars
- Brand, car and city model management
- Pagination and sorting for car listings
- Validation and exception handling

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **MySQL** (or any other relational database)
- **Gradle**
- **JWT** for authentication
- **MultipartFile** for file uploads

## Project Structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── gr.aueb.cf.carrentalapp
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       ├── repository
│   │   │       ├── model
│   │   │       ├── dto
│   │   │       ├── mapper
│   │   │       └── exceptions
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── gr.aueb.cf.carrentalapp
└── pom.xml
```

## Installation and Setup

### Prerequisites
- Java 17 or later
- Gradle 7x+
- MySQL (or compatible database)

### Clone the Repository
```bash
git clone https://github.com/PetrosZark/carrental-backend.git
cd carrental-backend
```

### Configure Database
Edit `application.properties` to configure the PostgreSQL database connection:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/carrental
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Car Endpoints
- **POST /api/cars** – Add a new car
- **PUT /api/cars/{id}** – Update car details
- **DELETE /api/cars/{id}** – Delete a car
- **GET /api/cars** – Get paginated list of cars

### User Endpoints
- **POST /api/users/register** – Register a new user
- **POST /api/users/login** – User login

### File Upload
- **POST /api/cars/{id}/upload** – Upload a photo for a car

## Exception Handling
- **AppObjectAlreadyExistsException** – Thrown when a car with the same license plate already exists.
- **AppObjectInvalidArgumentException** – Thrown when invalid brand or model IDs are provided.
- **IllegalArgumentException** – For general argument validation.

## Development Notes
- **CascadeType.ALL** is used for handling photo attachments to cars.
- Car deletions also remove associated photo files from disk.
- Transactions are managed using `@Transactional` to ensure consistency.
- `Mapper` class handles DTO to entity conversion.

## Contribution
1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Commit your changes.
4. Push to the branch.
5. Create a pull request.

## License
MIT License

