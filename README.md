# Arithmetic Operations API

------
## General info
This simple application allow to execute basic mathematical operations.


### Technologies and Dependencies

- Spring Boot (Version: 3.1.1)
- Lombok (Version: LATEST)
- Spring Web (Included in Spring Boot)
- Spring Data JPA (Included in Spring Boot)
- MySQL Connector (Version: 8.0.33)
- AWS SDK Secrets Manager (Version: 1.11.418)
- AWS SDK v2 Secrets Manager (Version: 2.17.29)
- Gson (Version: 2.8.7)
- Spring Security (Included in Spring Boot)
- JWT (JSON Web Token) (Version: 0.11.5)
- Log4j (Version: 1.3.8.RELEASE)


### Setup
1. Clone github repository <br />
2. Download maven dependencies <br />
3. Hit run button <br />

### Features

- Addition: Perform addition operations.
- Subtraction: Perform subtraction operations.
- Multiplication: Perform multiplication operations.
- Division: Perform division operations.
- Square: Calculate the square root of a number.
- Random String: Generate random strings using a third-party API (e.g., https://www.random.org/clients).
- Authentication and Authorization
- Pagination and Filtering (Operation Records)

------

## Postman

### Login 

    curl --location --request POST 'http://operations.us-east-1.elasticbeanstalk.com/operation/v1/auth' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "username": "jocarosa",
    "password": "12345"
    }'
### Records By Page
    curl --location --request POST 'http://operations.us-east-1.elasticbeanstalk.com/operation/records/v1' \
    --header 'Authorization: Bearer {{token}}  
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=7445A21CCABA85C64F7322A4196C4314' \
    --data-raw '{
    "action": "search",
    "recordPage": 0,
    "recordSize" : 1,
    "recordId" : 23
    }'

### Remove Record By Id

    curl --location --request POST 'http://operations.us-east-1.elasticbeanstalk.com/operation/records/v1' \
    --header 'Authorization: Bearer {{token}}' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=7445A21CCABA85C64F7322A4196C4314' \
    --data-raw '{
    "action": "remove",
    "recordId" : 25
    }'

---------------
## Operations

  * Addition
  * RandomString
  * Substraccion
  * Multiplication
  * Division


    curl --location --request POST 'http://operations.us-east-1.elasticbeanstalk.com/operation/v1' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2Nhcm9zYSIsImlhdCI6MTY5MDAwNTA0MiwiZXhwIjoxNjkwMDA1MjgyfQ.4n2dBGnTswFd83ne_CdvrE_OtF3GCa8UYg9wAXMzL5M' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=1BE48C07185CD63B1B45113D178B3A45' \
    --data-raw '{
    "operands": [2],
    "operationType" : "RandomString"
    }'

## Notes

To ensure proper functionality when working locally, it is essential to request and utilize the necessary secrets for JWT Token generation and AWS Secret Manager integration. Please direct any requests for sensitive secrets or access to the designated administrator.

Please note that the production URL `operations.us-east-1.elasticbeanstalk.com` should be accessed via Postman or other appropriate tools designed for API testing. Accessing the production URL directly through the browser might not yield the expected results, as it is typically reserved for API calls.
(See postman section for details).