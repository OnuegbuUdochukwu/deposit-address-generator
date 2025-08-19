package com.codewithudo.depositaddressgenerator.service;

import com.codewithudo.depositaddressgenerator.dto.AddressResponse;
import com.codewithudo.depositaddressgenerator.dto.DepositAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {

    private final RestTemplate restTemplate;

    @Value("${quidax.api.secret-key}")
    private String secretKey;

    public AddressService() {
        this.restTemplate = new RestTemplate();
    }

    public DepositAddress generateDepositAddress(String userId, String currency) {
        String url = "https://app.quidax.io/api/v1/users/" + userId + "/wallets/" + currency + "/addresses";

        // Create the authentication headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        // Create an HttpEntity with the headers. The body is null for this POST request.
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Execute the POST request
        AddressResponse response = restTemplate.postForObject(url, entity, AddressResponse.class);

        // Unwrap and return the address data
        if (response != null && "success".equals(response.getStatus())) {
            return response.getData();
        }

        return null;
    }
}
