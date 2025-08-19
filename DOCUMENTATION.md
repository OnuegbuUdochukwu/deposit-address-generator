# Project Documentation: Deposit Address Generator

## Project Overview
The Deposit Address Generator is a Spring Boot microservice that demonstrates how to create a new resource via an external, authenticated API. Its primary function is to send an authenticated POST request to the Quidax API to initiate the generation of a new cryptocurrency deposit address for a user.

This project highlights how to handle API operations that don't return data instantly (asynchronous operations) by correctly modeling the initial response which includes a record ID and a null address field.

### Endpoint
`POST /api/v1/users/{userId}/wallets/{currency}/addresses`: Initiates the creation of a new deposit address for the specified user and currency.

## Core Dependencies
- **spring-boot-starter-web**: Provides all necessary components for building REST APIs.
- **lombok**: A utility library used to reduce boilerplate code.

## Project Structure and Components
The project uses a standard layered architecture. The DTOs model the response from the Quidax API's address creation endpoint.

```
/dto/
 ├── DepositAddress.java  (Models the address creation record)
 └── AddressResponse.java   (Wrapper for the API's top-level response)
/service/
 └── AddressService.java    (Handles the authenticated POST request)
/controller/
 └── AddressController.java (API Endpoint Layer)
```

## Detailed Class Explanations

### The DTO Layer (The Data Models)
The DTOs are designed to match the JSON structure returned by the Quidax API after a POST request.

#### 📄 DepositAddress.java
**Purpose**: Represents the data record for the address generation request. It includes the id of the record and the address, which is initially null.

```java
@Data
public class DepositAddress {
    private String id;
    private String address;
    @JsonProperty("created_at")
    private String createdAt;
}
```

#### 📄 AddressResponse.java
**Purpose**: This DTO represents the top-level wrapper object that the Quidax API uses for this endpoint, containing the status and the DepositAddress data.

```java
@Data
public class AddressResponse {
    private String status;
    private DepositAddress data;
}
```

### service/AddressService.java - The Business Logic

This class contains the logic for creating and sending an authenticated POST request.

#### Key Components:

- `@Value("${quidax.api.secret-key}")`: Injects the secret key from application.properties.
- `HttpHeaders & HttpEntity`: These Spring classes are used to construct the HTTP request, specifically to add the `Authorization: Bearer <token>` header required for authenticated calls.
- `restTemplate.postForObject(...)`: This is the key RestTemplate method used to execute an HTTP POST request. It sends the request to the specified URL with the provided entity (containing headers) and automatically deserializes the JSON response into our AddressResponse class.

#### Code:

```Java
@Service
public class AddressService {

    private final RestTemplate restTemplate;

    @Value("${quidax.api.secret-key}")
    private String secretKey;

    public AddressService() {
        this.restTemplate = new RestTemplate();
    }

    public DepositAddress generateDepositAddress(String userId, String currency) {
        String url = "https://api.quidax.com/api/v1/users/" + userId + "/wallets/" + currency + "/addresses";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        AddressResponse response = restTemplate.postForObject(url, entity, AddressResponse.class);

        if (response != null && "success".equals(response.getStatus())) {
            return response.getData();
        }

        return null;
    }
}
```

### controller/AddressController.java - The API Layer

This class exposes our service's functionality to the web.

### Key Annotations:

- `@PostMapping`: This annotation specifically maps HTTP POST requests to this method. POST is the standard HTTP verb used for creating new resources.
- `@PathVariable`: Extracts the dynamic parts (userId and currency) from the URL path.

#### Code:

```Java
@RestController
@RequestMapping("/api/v1/users")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}/wallets/{currency}/addresses")
    public DepositAddress generateAddress(
            @PathVariable String userId,
            @PathVariable String currency) {
        return addressService.generateDepositAddress(userId, currency);
    }
}
```