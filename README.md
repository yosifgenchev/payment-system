# Payment system

## Prerequisites
- Java 17
- PostgreSQL database
- Create 'paymentsystem' database (can be parameterized in application.yml)
- Create 'payment_system' schema

## Authentication
Basic auth with the following credentials is used for both UI and API

- Username: admin
- Password: password


- Username: merchant2
- Password: password


- Username: merchant3
- Password: password

## NB
- **Transactions are currently generated manually by using the APIs mentioned below with basic authentication.**
- **Database migrations should happen automatically when running the application, but the prerequisites for creating the database and the schema mentioned above should be followed.**

## Build
```java
gradlew build
```

## Code linter
```java
gradlew check
```

## API

**Spring Data REST is used for API management**

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

#### TODO
**The APIs can be improved by skipping for example setting the status of the transaction, because obviously it should be done on server side instead of setting it explicitly.**
**Another thing to improve is to skip merchant whenever possible as it should be clear which customer is referred if the transaction is other than AUTHORIZE type.**
**One last thing would be the amount of the REFUND transaction, because it should be taken from the CHARGE transaction to which the REFUND transaction refers.**

Example:

- **Get all transaction**

GET /api/transactions

Response
```json
{
    "_embedded": {
        "authorizeTransactions": [
            {
                "createdDateTime": "2023-04-13T16:53:08.711369",
                "amount": 2000,
                "customerEmail": "customerEmail@example.org",
                "customerPhone": null,
                "status": "approved",
                "type": "AUTHORIZE",
                "transactionToBeModified": null,
                "modifyingStatus": false,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe"
                    },
                    "authorizeTransaction": {
                        "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe"
                    },
                    "merchant": {
                        "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe/merchant"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/transactions"
        },
        "profile": {
            "href": "http://localhost:8080/api/profile/transactions"
        },
        "search": {
            "href": "http://localhost:8080/api/transactions/search"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
    }
}
```

- **Create AUTHORIZE transaction**

POST /api/transactions
```json
{
  "amount":100.0,
  "status":"approved",
  "customerEmail":"customerEmail@example.org",
  "type":"AUTHORIZE",
  "merchant": "http://localhost:8080/merchants/<ID>"
}
```

Response
```json
{
    "createdDateTime": "2023-04-13T16:53:08.7113694",
    "amount": 2000,
    "customerEmail": "customerEmail@example.org",
    "customerPhone": null,
    "status": "approved",
    "type": "AUTHORIZE",
    "transactionToBeModified": null,
    "modifyingStatus": false,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe"
        },
        "authorizeTransaction": {
            "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe"
        },
        "merchant": {
            "href": "http://localhost:8080/api/authorizeTransaction/6e55800e-2d3f-43cd-94af-bf205e6198fe/merchant"
        }
    }
}
```


- **Create CHARGE transaction**

POST /api/transactions
```json
{
  "amount":100.0,
  "status":"approved",
  "customerEmail":"customerEmail@example.org",
  "type":"CHARGE",
  "merchant": "http://localhost:8080/merchants/<ID>",
  "authorizeTransaction": "http://localhost:8080/api/authorizeTransaction/<UUID>"
}
```

Response
```json
{
    "createdDateTime": "2023-04-13T16:59:50.2651158",
    "amount": 2000,
    "customerEmail": "customerEmail@example.org",
    "customerPhone": null,
    "status": "approved",
    "type": "CHARGE",
    "referencedTransaction": {
        "type": "AUTHORIZE",
        "createdDateTime": "2023-04-13T16:53:08.711369",
        "amount": 2000,
        "customerEmail": "customerEmail@example.org",
        "customerPhone": null,
        "status": "approved",
        "type": "AUTHORIZE",
        "transactionToBeModified": null,
        "modifyingStatus": false
    },
    "transactionToBeModified": null,
    "modifyingStatus": false,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/chargeTransaction/ed8d35b1-5e1c-4a9f-afa1-70de4af8e40b"
        },
        "chargeTransaction": {
            "href": "http://localhost:8080/api/chargeTransaction/ed8d35b1-5e1c-4a9f-afa1-70de4af8e40b"
        },
        "merchant": {
            "href": "http://localhost:8080/api/chargeTransaction/ed8d35b1-5e1c-4a9f-afa1-70de4af8e40b/merchant"
        },
        "authorizeTransaction": {
            "href": "http://localhost:8080/api/chargeTransaction/ed8d35b1-5e1c-4a9f-afa1-70de4af8e40b/authorizeTransaction"
        }
    }
}
```

- **Create REFUND transaction**

POST /api/transactions
```json
{
    "amount":100.0,
    "status":"approved",
    "customerEmail":"customerEmail@example.org",
    "type":"REFUND",
    "merchant": "http://localhost:8080/merchants/<ID>",
    "chargeTransaction": "http://localhost:8080/api/chargeTransaction/<UUID>"
}
```

Response
```json
{
    "createdDateTime": "2023-04-13T17:05:41.6513365",
    "amount": 2000,
    "customerEmail": "customerEmail@example.org",
    "customerPhone": null,
    "status": "approved",
    "type": "REFUND",
    "transactionToBeModified": {
        "type": "CHARGE",
        "createdDateTime": "2023-04-13T16:59:50.265116",
        "amount": 2000,
        "customerEmail": "customerEmail@example.org",
        "customerPhone": null,
        "status": "refunded",
        "type": "CHARGE",
        "referencedTransaction": {
            "type": "AUTHORIZE",
            "createdDateTime": "2023-04-13T16:53:08.711369",
            "amount": 2000,
            "customerEmail": "customerEmail@example.org",
            "customerPhone": null,
            "status": "approved",
            "type": "AUTHORIZE",
            "transactionToBeModified": null,
            "modifyingStatus": false
        },
        "transactionToBeModified": null,
        "modifyingStatus": false
    },
    "modifyingStatus": true,
    "referencedTransaction": {
        "type": "CHARGE",
        "createdDateTime": "2023-04-13T16:59:50.265116",
        "amount": 2000,
        "customerEmail": "customerEmail@example.org",
        "customerPhone": null,
        "status": "refunded",
        "type": "CHARGE",
        "referencedTransaction": {
            "type": "AUTHORIZE",
            "createdDateTime": "2023-04-13T16:53:08.711369",
            "amount": 2000,
            "customerEmail": "customerEmail@example.org",
            "customerPhone": null,
            "status": "approved",
            "type": "AUTHORIZE",
            "transactionToBeModified": null,
            "modifyingStatus": false
        },
        "transactionToBeModified": null,
        "modifyingStatus": false
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/refundTransaction/69ab5c06-6851-4302-acd8-be3facaaf0a4"
        },
        "refundTransaction": {
            "href": "http://localhost:8080/api/refundTransaction/69ab5c06-6851-4302-acd8-be3facaaf0a4"
        },
        "merchant": {
            "href": "http://localhost:8080/api/refundTransaction/69ab5c06-6851-4302-acd8-be3facaaf0a4/merchant"
        },
        "chargeTransaction": {
            "href": "http://localhost:8080/api/refundTransaction/69ab5c06-6851-4302-acd8-be3facaaf0a4/chargeTransaction"
        }
    }
}
```

- **Create REVERSAL transaction**

POST /api/transactions
```json
{
    "status":"approved",
    "customerEmail":"customerEmail@example.org",
    "type":"REVERSAL",
    "merchant": "http://localhost:8080/merchants/<ID>",
    "authorizeTransaction": "http://localhost:8080/api/authorizeTransaction/<UUID>"
}
```

```json
{
    "createdDateTime": "2023-04-13T17:08:06.1145114",
    "amount": null,
    "customerEmail": "customerEmail@example.org",
    "customerPhone": null,
    "status": "approved",
    "type": "REVERSAL",
    "transactionToBeModified": {
        "type": "AUTHORIZE",
        "createdDateTime": "2023-04-13T16:53:08.711369",
        "amount": 2000,
        "customerEmail": "customerEmail@example.org",
        "customerPhone": null,
        "status": "reversed",
        "type": "AUTHORIZE",
        "transactionToBeModified": null,
        "modifyingStatus": false
    },
    "modifyingStatus": true,
    "referencedTransaction": {
        "type": "AUTHORIZE",
        "createdDateTime": "2023-04-13T16:53:08.711369",
        "amount": 2000,
        "customerEmail": "customerEmail@example.org",
        "customerPhone": null,
        "status": "reversed",
        "type": "AUTHORIZE",
        "transactionToBeModified": null,
        "modifyingStatus": false
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/reversalTransaction/d0039765-286a-49f3-a3a8-87572680d901"
        },
        "reversalTransaction": {
            "href": "http://localhost:8080/api/reversalTransaction/d0039765-286a-49f3-a3a8-87572680d901"
        },
        "authorizeTransaction": {
            "href": "http://localhost:8080/api/reversalTransaction/d0039765-286a-49f3-a3a8-87572680d901/authorizeTransaction"
        },
        "merchant": {
            "href": "http://localhost:8080/api/reversalTransaction/d0039765-286a-49f3-a3a8-87572680d901/merchant"
        }
    }
}
```




