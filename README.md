# Deposit Address Generator API 📬

A Spring Boot REST API that makes an authenticated POST request to the Quidax API to generate a new deposit address for a specific cryptocurrency.

This project demonstrates how to handle asynchronous API operations where the initial response confirms the request, but the final data (the address) is generated in the background.

## Features

- Makes authenticated POST requests to create a new resource.
- Securely injects an API secret key from `application.properties`.
- Correctly models and parses the API's asynchronous response, which includes a record ID and an initially null address field.
- Built with a clean, layered architecture (Controller, Service, DTO).

## Technologies Used

- Java 17
- Spring Boot 3
- Spring MVC (`@PostMapping`)
- Maven
- Lombok

## Setup and Configuration

This application requires a personal Quidax API secret key to function.

1. **Add Your Key**: Open the `src/main/resources/application.properties` file and add your secret key:

   ```properties
   quidax.api.secret-key=YOUR_SECRET_KEY_GOES_HERE
   ```

2. **Secure Your Key**: To prevent your key from being uploaded to GitHub, open the `.gitignore` file in the project's root directory and add the following line:

   ```plaintext
   application.properties
   ```

## API Endpoint

| Method | Endpoint                                            | Description                                                  |
|--------|-----------------------------------------------------|--------------------------------------------------------------|
| POST   | `/api/v1/users/{userId}/wallets/{currency}/addresses` | Generate a new deposit address for a specific currency.     |

## Export to Sheets

### Example Usage:

This endpoint must be called with a POST request using a tool like Postman. Replace `{userId}` with `me` and `{currency}` with a symbol like `btc`.

```plaintext
POST http://localhost:8080/api/v1/users/me/wallets/btc/addresses
```

### Example Response:

A successful request will return a JSON object confirming the creation request. The address field is null because it is generated in the background.

```json
{
    "id": "a4d9971e-6a7e-4b17-a6f0-25baad8595cc",
    "address": null,
    "createdAt": "2025-08-19T22:15:58.026Z"
}
```

## Getting Started

To get a local copy up and running, follow these steps.

### Prerequisites

- JDK (Java Development Kit) 17 or later
- Maven
- A valid Quidax API Secret Key

### Installation & Running the App

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/deposit-address-generator.git
   ```

2. Navigate to the project directory:

   ```bash
   cd deposit-address-generator
   ```

3. Configure your secret key as described in the "Setup and Configuration" section.

4. Run the application using the Maven wrapper:

   On macOS/Linux:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```bash
   mvnw.cmd spring-boot:run
   ```

The application will start on `http://localhost:8080`.