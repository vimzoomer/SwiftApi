# Swift API - Spring Boot Application

## Project Overview
This is a Spring Boot application for managing SWIFT codes with RESTful APIs. It includes:
- Spring Boot for backend logic
- Spring Data JPA for database operations
- Hibernate for ORM
- Jakarta Validation for request validation
- JUnit 5 for unit testing

### Clone the Repository
```sh
 https://github.com/vimzoomer/SwiftApi
 cd SwiftApi
```

### Build and Run with Gradle
```sh
./gradlew build
```

### Build and Run with Docker Compose
```sh
docker-compose up --build
```

Be aware that it may require sudo permissions, build requires Docker daemon socket access. 

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/v1/swift-codes/{swiftCode}` | Get SWIFT code by ID |
| GET | `/v1/swift-codes/country/{countryISO2}` | Get SWIFT codes by country |
| POST | `/v1/swift-codes` | Add new SWIFT code |
| DELETE | `/v1/swift-codes/{swiftCode}` | Delete a SWIFT code |
| POST | `/v1/swift-codes/upload-csv` | Upload CSV file to update SWIFT codes |

### Example Request using curl (Add SWIFT Code)
```sh
curl -X POST "http://localhost:8080/v1/swift-codes" \
-H "Content-Type: application/json" \
-d '{
"swiftCode": "ABCD1234",
"address": "123 Bank St.",
"bankName": "Example Bank",
"countryISO2": "US",
"countryName": "United States",
"isHeadquarter": true
}'

```

### Example Request (Upload CSV)
```sh
curl -X POST http://localhost:8080/v1/swift-codes/upload-csv \
  -F "file=@parse.csv"
```

### Run Tests
```sh
./gradlew test
```

## Technologies Used
- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Gradle
- JUnit 5
- Postgres


