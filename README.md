# Payment system

## Prerequisites
- Java 17
- PostgreSQL database
- Create 'paymentsystem' database (can be parameterized in application.yml)
- Create 'payment_system' schema

## Authentication
Basic auth with default Spring credentials is used for both UI and API

- Username: user
- Password: _generated in the console when running the application_ 

## NB
- **Merchants and transactions are currently generated manually by using the APIs mentioned below with basic authentication.**
- **Database migrations should happen automatically when running the application, but the prerequisites for creating the database and the schema mentioned above should be followed.**

## Build
```java
gradlew build
```

## Code linter
```java
gradlew check
```

## Merchants API

Example

GET /api/merchants

POST /api/merchants

```json
{
    "name":"Merchant name",
    "description":"description",
    "email":"merchant@example.org",
    "status":"active"
}
```

## Transactions API

Example:

GET /api/transactions

POST /api/transactions

- Authorize
```json
{
  "amount":100.0,
  "status":"approved",
  "customerEmail":"customerEmail@example.org",
  "type":"AUTHORIZE",
  "merchant": "http://localhost:8080/merchants/1"
}
```

- Charge
```json
{
  "amount":100.0,
  "status":"approved",
  "customerEmail":"customerEmail@example.org",
  "type":"CHARGE",
  "merchant": "http://localhost:8080/merchants/1",
  "referencedTransaction": "http://localhost:8080/api/authorizeTransaction/<UUID>"
}
```

- Refund
```json
{
    "amount":100.0,
    "status":"approved",
    "customerEmail":"customerEmail@example.org",
    "type":"REFUND",
    "merchant": "http://localhost:8080/merchants/1",
    "referencedTransaction": "http://localhost:8080/api/chargeTransaction/<UUID>"
}
```

- Reversal
```json
{
    "status":"approved",
    "customerEmail":"customerEmail@example.org",
    "type":"REVERSAL",
    "merchant": "http://localhost:8080/merchants/1",
    "referencedTransaction": "http://localhost:8080/api/authorizeTransaction/<UUID>"
}
```

## Tasks to be done
The following tasks fell of the scope and should be done in the future:

- Ensure you have merchant and admin user roles (UI)
- Inputs and tasks:
  - Imports new merchants and admins from CSV (rake task)
  - A background Job for deleting transactions older than an hour (cron job)

- Presentation:
  - edit merchants



