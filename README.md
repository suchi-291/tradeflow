# TradeFlow

Trade Processing and Repository System built using Java Spring Boot and MySQL.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## Features Implemented

### Day 1
- Counterparty entity
- Counterparty CRUD APIs
- Layered architecture
  - Controller
  - Service
  - Repository
- MySQL integration
- JPA persistence

### APIs

#### Create Counterparty

POST /counterparties

Sample Request:

```json
{
  "lei": "LEI001",
  "name": "HDFC Bank"
}
```

#### Get All Counterparties

GET /counterparties

#### Get Counterparty By ID

GET /counterparties/{id}

## Architecture

Controller → Service → Repository → Database

## Future Scope

- Instrument module
- Trade module
- DTO mapping
- Validation
- Exception handling
- Trade lifecycle management

## Author

Suchitha R