# Car Rental App - Backend

## Overview
The Car Rental App backend is a Spring Boot application that provides RESTful APIs 
for managing cars, users, and rental services. It handles operations such as car 
registration, updates, deletions, and user authentication. The backend also manages 
photo uploads for car listings and ensures data integrity through exception handling 
and transactional operations.

## Features
- Car management (CRUD operations)
- User registration and authentication
- Photo upload and attachment to cars
- Brand, car and city model management
- Pagination and sorting for car listings
- Validation and exception handling

## Technologies Used
- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **MySQL** 
- **Gradle**
- **JWT** for authentication
- **MultipartFile** for file uploads

## Project Structure
```
carrentalapp
├── src
│   ├── main
│   │   ├── java
│   │   │   └── gr.aueb.cf.carrentalapp
│   │   │       ├── authentication      
│   │   │       ├── config              
│   │   │       ├── core                
│   │   │       │   ├── enums           
│   │   │       │   ├── exceptions     
│   │   │       │   ├── filters        
│   │   │       │   └── specifications  
│   │   │       ├── dto                 
│   │   │       ├── mapper              
│   │   │       ├── model              
│   │   │       ├── repository          
│   │   │       ├── rest                
│   │   │       ├── security           
│   │   │       └── service             
│   │   └── resources
│   │       ├── application.properties  
│   │       └── sql                     
│   └── test
│       └── java
│           └── gr.aueb.cf.carrentalapp 
└── build.gradle                        

```

## Installation and Setup

### Prerequisites
- Java 17 or later
- Gradle 8x+
- MySQL (or compatible database)
- IntelliJ IDEA or any suitable Java IDE

### Clone the Repository
```bash
git clone https://github.com/PetrosZark/CarRentalApp-Java_Backend.git
```

### Open the Project in your Java IDE

### Configure Database
Edit `application.properties` to configure the MySQL database connection

### Build and Run
```bash
./gradlew clean build
./gradlew bootRun
```

### 🚀 Usage
Once the server is up and running, you can interact with the REST API endpoints 
using tools like Postman or integrate directly with the [Angular frontend]
(https://github.com/PetrosZark/CarRentalApp-Angular_Frontend), which is built to 
complement this backend.

## API Endpoints

### Car Endpoints
- **GET /api/home/garage** – Get paginated list of cars
- **GET /api/home/garage/{carId}** – Get car by id
- **POST /api/home/garage** – Add a new car
- **POST /api/home/garage/{carId}/upload-image** – Upload car image
- **PUT /api/home/garage/update/{carId}** – Update car details
- **DELETE /api/home/garage/delete/{carId}** – Delete a car
- **DELETE /api/home/garage/{carId}/delete-image** – Delete car image

### Entities Endpoints (SUPER_ADMIN authorized only)
- **GET /api/home/manage-entities/users** – Get paginated list of users
- **PATCH /api/home/manage-entities/users/{username}/toggle-status** – Toggle user status
- **PATCH /api/home/manage-entities/users/{username}/change-role** – Change user role
- **DELETE /api/home/manage-entities/users/{username}** – Delete user
- **POST /api/home/manage-entities/brands** – Add new brand
- **POST /api/home/manage-entities/carmodels** – Add new model
- **POST /api/home/manage-entities/cities** – Add new city


