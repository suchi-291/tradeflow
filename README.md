# TradeFlow

Trade Processing and Repository System built using Java Spring Boot and MySQL.
People trading financial products with each other
There are only 3 important business objects:

####Object	
Counterparty ->	WHO is trading (The company/bank participating in trade)
Instrument	-> WHAT is being traded (The financial product being traded)
Trade -> 	The actual transaction (The actual deal/transaction)

####Relationship Understanding
1. One Counter party can participate in many trades
2. One instrument can appear in many trades
3. Trade connects buyer, seller, instrument, Quantity, price, dates

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

### Day 2

Instrument -> ISIN(International Securities Identification Number), type (Enum is used - because only predefined values are allowed here)


## Author

Suchitha R