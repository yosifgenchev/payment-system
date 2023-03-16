# Payment system

## Prerequisites
- Java 17
- PostgreSQL database

## Build
```java
gradlew build
```

## Code linter
```java
gradlew check
```

## Transactions API

Example:

POST /api/transactions

```json
{
    "amount":100.0,
    "status":"approved",
    "customerEmail":"customerEmail@example.org",
    "merchant_id":"1",
    "referred_transaction_uuid":"310d3a64-cef9-4c07-86c4-1a7088b74bcd",
    "dtype":"ChargeTransaction"
}
```

## Merchant API

Example

POST /api/merchants

```json
{
    "name":"Merchant name",
    "description":"description",
    "email":"merchant@example.org",
    "status":"active"
}
```