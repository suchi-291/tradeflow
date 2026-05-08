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


# TradeFlow: Day 2 Technical Documentation

## 1. Objective

The goal of Day 2 is to move from simple CRUD to a real business domain model.

Until Day 1, we only had:

```text
CounterParty
```

Now we added two more important entities:

```text
Instrument
Trade
```

Together, these represent a basic trade processing system.

The business idea is:

```text
A buyer counterparty buys/sells an instrument from/to a seller counterparty.
```

Example:

```text
HDFC Bank buys Reliance Equity from ICICI Bank.
```

So the three main business objects are:

| Entity       | Meaning                |
| ------------ | ---------------------- |
| CounterParty | Who is trading         |
| Instrument   | What is being traded   |
| Trade        | The actual transaction |

---

# 2. Database Design

## 2.1 CounterParty Table

This table stores banks, companies, or institutions involved in trades.

Example:

```text
HDFC Bank
ICICI Bank
SBI
Goldman Sachs
```

Each counterparty has:

```text
id
lei
name
createdAt
```

`id` is the internal database identifier.

`lei` is the legal entity identifier, used in financial systems to uniquely identify a company.

---

## 2.2 Instrument Table

We created this table:

```sql
CREATE TABLE instrument (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isin VARCHAR(12) UNIQUE,
    type ENUM('EQUITY','BOND','DERIVATIVE'),
    currency CHAR(3)
);
```

This table stores financial products that can be traded.

Example:

| id | isin         | type   | currency |
| -- | ------------ | ------ | -------- |
| 1  | INE002A01018 | EQUITY | INR      |
| 2  | US91282CFZ95 | BOND   | USD      |

### Why `isin`?

ISIN means International Securities Identification Number.

It uniquely identifies a financial instrument.

Example:

```text
Reliance Equity = INE002A01018
```

### Why `type`?

`type` tells what kind of instrument it is.

Allowed values:

```text
EQUITY
BOND
DERIVATIVE
```

We used an enum because instrument type should be controlled. We should not allow random values like:

```text
stock
share
eqty
abc
```

### Why `currency CHAR(3)`?

Currency codes are 3-letter standard values.

Examples:

```text
INR
USD
EUR
```

---

## 2.3 Trade Table

We created this table:

```sql
CREATE TABLE trade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    buyer_id BIGINT,
    seller_id BIGINT,
    instrument_id BIGINT,
    trade_date DATE,
    settlement_date DATE,
    quantity DECIMAL(19,4),
    price DECIMAL(19,4),
    status VARCHAR(20),
    version INT DEFAULT 0,
    created_at DATETIME,
    updated_at DATETIME,

    CONSTRAINT fk_trade_buyer 
        FOREIGN KEY (buyer_id) REFERENCES counter_party(id),

    CONSTRAINT fk_trade_seller 
        FOREIGN KEY (seller_id) REFERENCES counter_party(id),

    CONSTRAINT fk_trade_instrument 
        FOREIGN KEY (instrument_id) REFERENCES instrument(id)
);
```

This table stores actual trade transactions.

Example:

| id | buyer_id | seller_id | instrument_id | quantity | price  |
| -- | -------- | --------- | ------------- | -------- | ------ |
| 1  | 1        | 2         | 1             | 1000     | 250.50 |

This means:

```text
Counterparty 1 bought Instrument 1 from Counterparty 2.
```

---

# 3. Why We Used Foreign Keys

In the `trade` table, we do not store the full buyer name, seller name, or instrument details.

Instead, we store IDs:

```text
buyer_id
seller_id
instrument_id
```

These IDs refer to other tables.

Example:

```sql
buyer_id REFERENCES counter_party(id)
```

This means:

```text
buyer_id must be a valid counter_party id.
```

This protects data quality.

If we try to insert:

```text
buyer_id = 999
```

but no counterparty with id `999` exists, MySQL will reject it.

That is why foreign keys are important.

---

# 4. Entity Design

## 4.1 Why Entities Exist

A database table is represented in Java using an entity class.

Example:

```text
instrument table → Instrument entity
trade table → Trade entity
counter_party table → CounterParty entity
```

JPA/Hibernate uses these entity classes to map Java objects to database rows.

---

# 5. Instrument Entity

We created:

```java
@Entity
@Table(name = "instrument")
public class Instrument {
```

### Why `@Entity`?

`@Entity` tells Spring/JPA:

```text
This Java class represents a database table.
```

Without `@Entity`, Hibernate will ignore the class.

### Why `@Table(name = "instrument")`?

This tells Hibernate the exact table name in MySQL.

Because our table name is:

```text
instrument
```

So Java entity should map to the same table.

---

## 5.1 Instrument Fields

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### Why `@Id`?

It marks the primary key of the entity.

### Why `@GeneratedValue(strategy = GenerationType.IDENTITY)`?

Because MySQL uses:

```sql
AUTO_INCREMENT
```

So Java should not manually generate ID. MySQL generates it.

---

```java
private String isin;
```

This maps to:

```sql
isin VARCHAR(12)
```

---

```java
@Enumerated(EnumType.STRING)
private InstrumentType type;
```

### Why enum?

Because instrument type has fixed allowed values:

```text
EQUITY
BOND
DERIVATIVE
```

### Why `EnumType.STRING`?

It stores enum values as text in DB.

Good:

```text
EQUITY
BOND
DERIVATIVE
```

Bad if not used:

```text
0
1
2
```

Storing strings is safer and readable.

---

```java
private String currency;
```

This maps to:

```sql
currency CHAR(3)
```

Examples:

```text
INR
USD
EUR
```

---

# 6. Trade Entity

The `Trade` entity is the most important entity.

It represents the actual transaction.

```java
@Entity
@Table(name = "trade")
public class Trade {
```

---

## 6.1 Trade ID

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Same logic as other entities.

MySQL auto-generates the trade ID.

---

# 7. Relationship Mapping

This is the main Day 2 concept.

In MySQL, the `trade` table stores:

```text
buyer_id
seller_id
instrument_id
```

But in Java, we do not write:

```java
private Long buyerId;
private Long sellerId;
private Long instrumentId;
```

Instead, we write:

```java
private CounterParty buyer;
private CounterParty seller;
private Instrument instrument;
```

Why?

Because JPA works with object relationships.

Database stores IDs.
Java works with objects.

---

## 7.1 Buyer Mapping

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "buyer_id")
private CounterParty buyer;
```

### What does this mean?

It means:

```text
Many trades can have one buyer.
```

Example:

| trade_id | buyer |
| -------- | ----- |
| 1        | HDFC  |
| 2        | HDFC  |
| 3        | HDFC  |

So from the Trade side:

```text
Many Trades → One CounterParty
```

That is why we use:

```java
@ManyToOne
```

### What does `@JoinColumn(name = "buyer_id")` mean?

It tells Hibernate:

```text
Use the buyer_id column in the trade table to connect to counter_party.id.
```

So Java sees:

```java
trade.getBuyer()
```

But MySQL stores:

```text
buyer_id = 1
```

---

## 7.2 Seller Mapping

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "seller_id")
private CounterParty seller;
```

Buyer and seller both come from the same table:

```text
counter_party
```

But they have different roles in a trade.

That is why we need two different join columns:

```text
buyer_id
seller_id
```

If we did not specify `@JoinColumn`, Hibernate may not understand clearly which column to use.

---

## 7.3 Instrument Mapping

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "instrument_id")
private Instrument instrument;
```

This means:

```text
Many trades can involve the same instrument.
```

Example:

| trade_id | instrument      |
| -------- | --------------- |
| 1        | Reliance Equity |
| 2        | Reliance Equity |
| 3        | Reliance Equity |

So again:

```text
Many Trades → One Instrument
```

---

# 8. Why We Did Not Add `@OneToMany` in CounterParty and Instrument

We initially thought about adding:

```java
@OneToMany(mappedBy = "buyer")
private List<Trade> buyTrades;
```

But we skipped it for now.

Why?

Because Day 2 only requires:

```text
Trade should know buyer, seller, and instrument.
```

We do not yet need:

```text
CounterParty should know all trades.
Instrument should know all trades.
```

Adding `@OneToMany` too early can create confusion and JSON recursion issues.

So for now, we keep the relationship unidirectional:

```text
Trade → CounterParty
Trade → Instrument
```

That is enough.

---

# 9. Lazy Fetching

We used:

```java
@ManyToOne(fetch = FetchType.LAZY)
```

### What does LAZY mean?

It means:

```text
Do not load related object immediately.
Load it only when needed.
```

Example:

When we fetch a trade, Hibernate first loads only the trade row.

It does not immediately load buyer, seller, and instrument.

Only when we call:

```java
trade.getBuyer().getName()
```

then Hibernate loads buyer details.

### Why use LAZY?

Because it improves performance.

If every trade automatically loaded buyer, seller, instrument, and more related data, the system may become slow.

Enterprise systems prefer lazy loading unless related data is definitely needed.

---

# 10. Trade Fields

```java
@Column(name = "trade_date")
private LocalDate tradeDate;
```

We used `@Column` because Java uses camelCase:

```text
tradeDate
```

But MySQL uses snake_case:

```text
trade_date
```

So we explicitly mapped the column.

Same for:

```java
@Column(name = "settlement_date")
private LocalDate settlementDate;
```

```java
@Column(name = "created_at")
private LocalDateTime createdAt;
```

```java
@Column(name = "updated_at")
private LocalDateTime updatedAt;
```

---

# 11. BigDecimal for Quantity and Price

We used:

```java
private BigDecimal quantity;
private BigDecimal price;
```

instead of:

```java
double
```

Why?

Because financial systems need accuracy.

`double` can create decimal precision errors.

For money, quantity, price, notional values, financial systems prefer:

```java
BigDecimal
```

This maps well to:

```sql
DECIMAL(19,4)
```

---

# 12. Trade Status

We used:

```java
@Enumerated(EnumType.STRING)
private TradeStatus status;
```

Trade status is a controlled lifecycle value.

Examples:

```text
NEW
VALIDATED
MATCHED
SETTLED
CANCELLED
```

A trade should not have random status like:

```text
done
abc
okay
```

So we use enum.

---

# 13. Version Field

We used:

```java
@Version
private Integer version;
```

This is for optimistic locking.

It protects the row from accidental overwrite when multiple users/processes update the same trade.

Example:

```text
User A updates trade
User B updates same trade using old data
```

`@Version` helps detect this conflict.

Hibernate automatically increases the version on update.

---

# 14. Audit Fields

We added:

```java
private LocalDateTime createdAt;
private LocalDateTime updatedAt;
```

These are audit fields.

They help track:

```text
When was this trade created?
When was this trade last updated?
```

---

## 14.1 `@PrePersist`

```java
@PrePersist
public void beforeSave() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();

    if (this.status == null) {
        this.status = TradeStatus.NEW;
    }
}
```

This runs automatically before first insert.

It sets:

```text
createdAt
updatedAt
status = NEW
```

We do this because these values should be controlled by backend, not user.

---

## 14.2 `@PreUpdate`

```java
@PreUpdate
public void beforeUpdate() {
    this.updatedAt = LocalDateTime.now();
}
```

This runs before update.

It changes only:

```text
updatedAt
```

It does not change `createdAt`, because created date should remain original.

---

# 15. Why DTOs Were Created

DTO means:

```text
Data Transfer Object
```

A DTO is a separate class used for API request or API response.

We created DTOs because:

```text
Entity is for database.
DTO is for API.
```

We should not expose entity directly in APIs.

---

# 16. Why We Created `TradeCreateRequest`

The API user should not send full objects like this:

```json
{
  "buyer": {
    "id": 1,
    "name": "HDFC Bank"
  }
}
```

That is messy.

Instead, API should send only IDs:

```json
{
  "buyerId": 1,
  "sellerId": 2,
  "instrumentId": 1,
  "tradeDate": "2026-05-07",
  "settlementDate": "2026-05-09",
  "quantity": 1000,
  "price": 250.50
}
```

That is why we created:

```java
TradeCreateRequest
```

It represents the input coming from Postman/frontend.

---

## 16.1 Why Request DTO Does Not Have ID

Because:

```text
id is generated by database.
```

User should not provide it.

---

## 16.2 Why Request DTO Does Not Have Status

Because:

```text
new trade should always start as NEW.
```

Backend should set status automatically.

---

## 16.3 Why Request DTO Does Not Have Version

Because:

```text
version is managed by Hibernate.
```

User should not control it.

---

## 16.4 Why Request DTO Does Not Have createdAt / updatedAt

Because:

```text
timestamps are backend-controlled audit fields.
```

They are set automatically using:

```java
@PrePersist
@PreUpdate
```

---

# 17. Why We Created Response DTOs

After saving a trade, we do not want to return raw entity.

We want a clean API response.

Response should show:

```json
{
  "id": 1,
  "buyer": {
    "id": 1,
    "lei": "LEI001",
    "name": "HDFC Bank"
  },
  "seller": {
    "id": 2,
    "lei": "LEI002",
    "name": "ICICI Bank"
  },
  "instrument": {
    "id": 1,
    "isin": "INE002A01018",
    "type": "EQUITY",
    "currency": "INR"
  },
  "status": "NEW"
}
```

This response is readable and frontend-friendly.

---

# 18. Why `CounterPartyResponse` Was Created

Buyer and seller are both counterparties.

So instead of creating:

```text
BuyerResponse
SellerResponse
```

we created one reusable DTO:

```text
CounterPartyResponse
```

Then in `TradeResponse`, we use it twice:

```java
private CounterPartyResponse buyer;
private CounterPartyResponse seller;
```

This is cleaner because buyer and seller have the same structure.

---

# 19. Why `InstrumentResponse` Was Created

Instrument has its own response structure:

```text
id
isin
type
currency
```

So we created:

```java
InstrumentResponse
```

Then `TradeResponse` can include:

```java
private InstrumentResponse instrument;
```

---

# 20. Why `TradeResponse` Was Created

`TradeResponse` is the complete API response after creating or fetching a trade.

It combines:

```text
trade details
buyer details
seller details
instrument details
status
audit fields
```

This gives the frontend/Postman a clean response without exposing database internals.

---

# 21. Overall Flow So Far

The flow we are building is:

```text
Postman sends TradeCreateRequest
↓
TradeService receives request
↓
Service finds buyer by buyerId
↓
Service finds seller by sellerId
↓
Service finds instrument by instrumentId
↓
Service creates Trade entity
↓
Repository saves Trade
↓
TradeMapper converts Trade entity to TradeResponse
↓
Controller returns TradeResponse
```

---

# 22. Why This Architecture Is Good

This is enterprise-style backend design.

| Layer      | Responsibility           |
| ---------- | ------------------------ |
| Controller | Receives API request     |
| DTO        | Defines API input/output |
| Service    | Business logic           |
| Repository | Database access          |
| Entity     | Database mapping         |
| Mapper     | Converts entity to DTO   |

Each layer has one job.

That makes the application easier to maintain, test, and debug.

---

# 23. What We Have Completed So Far

Completed:

```text
CounterParty entity and APIs
Instrument table
Trade table
Instrument entity
Trade entity
Enums
TradeCreateRequest DTO
CounterPartyResponse DTO
InstrumentResponse DTO
TradeResponse DTO

````md
---

# 24. TradeMapper

After creating DTOs, the next challenge was:

```text
How do we convert Trade entity into TradeResponse DTO?
````

For this purpose, a separate mapper layer was introduced.

---

## 24.1 Why Mapper Layer Was Needed

The `Trade` entity contains:

```java
private CounterParty buyer;
private CounterParty seller;
private Instrument instrument;
```

But API response should return:

```json
{
  "buyer": {
    "id": 1,
    "name": "HDFC Bank"
  }
}
```

This means entity objects must be converted into response DTOs.

That conversion logic was separated into:

```text
TradeMapper
```

This keeps:

* controller clean
* service clean
* conversion logic centralized

---

## 24.2 TradeMapper Implementation

```java
@Component
public class TradeMapper {

    public TradeResponse toTradeResponse(Trade trade) {

        CounterPartyResponse buyerResponse =
                toCounterPartyResponse(trade.getBuyer());

        CounterPartyResponse sellerResponse =
                toCounterPartyResponse(trade.getSeller());

        InstrumentResponse instrumentResponse =
                toInstrumentResponse(trade.getInstrument());

        return new TradeResponse(
                trade.getId(),
                buyerResponse,
                sellerResponse,
                instrumentResponse,
                trade.getTradeDate(),
                trade.getSettlementDate(),
                trade.getQuantity(),
                trade.getPrice(),
                trade.getStatus(),
                trade.getVersion(),
                trade.getCreatedAt(),
                trade.getUpdatedAt()
        );
    }
}
```

---

## 24.3 Nested DTO Conversion

Trade response contains nested DTOs.

Example:

```json
{
  "buyer": {
    "id": 1,
    "name": "HDFC Bank"
  }
}
```

To support this, helper methods were created:

```java
private CounterPartyResponse toCounterPartyResponse(...)
```

and

```java
private InstrumentResponse toInstrumentResponse(...)
```

This creates reusable conversion logic.

---

# 25. Repository Layer

Repositories were introduced for database access.

---

## 25.1 TradeRepository

```java
public interface TradeRepository
        extends JpaRepository<Trade, Long> {
}
```

---

## 25.2 Purpose of Repository Layer

Repository layer abstracts database operations.

Spring Data JPA automatically provides:

```text
save()
findById()
findAll()
deleteById()
```

without writing SQL queries manually.

---

# 26. Service Layer

The service layer contains business logic.

This is the most important layer in enterprise backend applications.

---

# 26.1 Why Service Layer Was Needed

The request DTO contains only IDs:

```json
{
  "buyerId": 1,
  "sellerId": 2
}
```

But the Trade entity expects:

```java
private CounterParty buyer;
private CounterParty seller;
```

So the service layer performs:

```text
ID → Entity Object conversion
```

---

# 26.2 Trade Creation Workflow

The workflow implemented in the service layer is:

```text
Receive TradeCreateRequest
↓
Fetch buyer from DB
↓
Fetch seller from DB
↓
Fetch instrument from DB
↓
Create Trade entity
↓
Set entity relationships
↓
Save Trade
↓
Convert entity to TradeResponse
```

---

# 26.3 Fetching Buyer Entity

```java
CounterParty buyer =
    counterPartyRepository
        .findById(request.getBuyerId())
        .orElseThrow();
```

This fetches the buyer object from the database.

Equivalent SQL:

```sql
SELECT * FROM counter_party WHERE id = 1;
```

---

# 26.4 Fetching Seller Entity

```java
CounterParty seller =
    counterPartyRepository
        .findById(request.getSellerId())
        .orElseThrow();
```

---

# 26.5 Fetching Instrument Entity

```java
Instrument instrument =
    instrumentRepository
        .findById(request.getInstrumentId())
        .orElseThrow();
```

---

# 26.6 Creating Trade Entity

```java
Trade trade = new Trade();
```

The trade object is then populated using request data.

---

## Relationship Assignment

```java
trade.setBuyer(buyer);
trade.setSeller(seller);
trade.setInstrument(instrument);
```

This is where JPA relationships become important.

Even though database stores:

```text
buyer_id
seller_id
instrument_id
```

Java works entirely with objects.

Hibernate internally extracts IDs while saving.

---

# 26.7 Saving Trade

```java
Trade savedTrade =
        tradeRepository.save(trade);
```

Hibernate internally generates SQL INSERT statement.

---

# 26.8 Returning Response DTO

```java
return tradeMapper.toTradeResponse(savedTrade);
```

The mapper converts entity into clean API response.

---

# 27. Controller Layer

The controller layer handles HTTP requests.

---

# 27.1 TradeController

```java
@RestController
public class TradeController {
}
```

---

## POST API

```java
@PostMapping("/trades")
```

This endpoint accepts trade creation requests.

---

# 27.2 Request Flow

```text
Postman JSON
↓
TradeCreateRequest DTO
↓
TradeService
↓
TradeRepository
↓
Database
```

---

# 27.3 Response Flow

```text
Database
↓
Trade Entity
↓
TradeMapper
↓
TradeResponse DTO
↓
Postman Response
```

---

# 28. Structured API Response

The API now returns nested structured JSON.

Example:

```json
{
  "id": 1,
  "buyer": {
    "id": 1,
    "lei": "LEI001",
    "name": "HDFC Bank"
  },
  "seller": {
    "id": 2,
    "lei": "LEI002",
    "name": "ICICI Bank"
  },
  "instrument": {
    "id": 1,
    "isin": "INE002A01018",
    "type": "EQUITY",
    "currency": "INR"
  },
  "tradeDate": "2026-05-07",
  "settlementDate": "2026-05-09",
  "quantity": 1000,
  "price": 250.50,
  "status": "NEW"
}
```

---

# 29. Derived Query Methods

Spring Data JPA allows query generation from method names.

---

## 29.1 Find Trades by Status

```java
List<Trade> findByStatus(TradeStatus status);
```

Spring automatically generates SQL query equivalent to:

```sql
SELECT * FROM trade WHERE status = ?;
```

No manual query writing required.

---

# 30. Streams API

Java Streams were introduced while converting entity lists into DTO lists.

---

## 30.1 Stream Mapping

```java
return trades.stream()
        .map(tradeMapper::toTradeResponse)
        .toList();
```

---

## 30.2 Meaning

```text
Take each Trade entity
↓
Convert into TradeResponse DTO
↓
Collect into List
```

Equivalent traditional loop:

```java
List<TradeResponse> responses = new ArrayList<>();

for (Trade trade : trades) {
    responses.add(tradeMapper.toTradeResponse(trade));
}
```

Streams provide cleaner functional-style code.

---

# 31. Query Parameters

The following endpoint was implemented:

```http
GET /trades/by-status?status=NEW
```

---

## 31.1 RequestParam

```java
@RequestParam TradeStatus status
```

Spring automatically converts:

```text
NEW
```

into:

```java
TradeStatus.NEW
```

because status is enum.

---

# 32. APIs Implemented

---

## CounterParty APIs

```http
POST /counterparties
GET /counterparties
GET /counterparties/{id}
```

---

## Instrument APIs

```http
POST /instruments
GET /instruments
GET /instruments/{id}
```

---

## Trade APIs

```http
POST /trades
GET /trades/by-status?status=NEW
```

---

# 33. Key Concepts Learned

The following enterprise backend concepts were implemented and understood during Day 2:

* JPA entity relationships
* Many-to-One mapping
* Foreign keys
* Lazy fetching
* DTO architecture
* Mapper pattern
* Repository abstraction
* Service-layer business logic
* Audit fields
* Enums
* Versioning
* Streams API
* Derived query methods
* Query parameters
* Layered architecture

---

# 34. Final Architecture

```text
Controller
↓
Service
↓
Repository
↓
Database
```

---

# 35. DTO Flow Summary

```text
Postman Request
↓
TradeCreateRequest DTO
↓
Service Layer
↓
Trade Entity
↓
Database
↓
TradeMapper
↓
TradeResponse DTO
↓
Postman Response
```

---

# 36. Enterprise Design Principles Applied

| Principle            | Purpose                  |
| -------------------- | ------------------------ |
| Layered Architecture | Separation of concerns   |
| DTO Pattern          | Clean API contracts      |
| Repository Pattern   | Abstract DB access       |
| Mapper Pattern       | Entity/DTO conversion    |
| Enum Usage           | Controlled values        |
| Audit Fields         | Record tracking          |
| Versioning           | Concurrency safety       |
| Lazy Loading         | Performance optimization |

---

# 37. Current Project Status

Completed:

* Counterparty module
* Instrument module
* Trade module
* Entity relationships
* DTO architecture
* Mapper layer
* Trade creation workflow
* Trade filtering by status

Upcoming:

* Exception handling
* Validation
* Structured error responses
* Trade lifecycle transitions
* Logging
* Global exception handling
* Advanced querying

---

```
```



## Author

Suchitha R