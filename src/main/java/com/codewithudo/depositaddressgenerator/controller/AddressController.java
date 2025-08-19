package com.codewithudo.depositaddressgenerator.controller;

import com.codewithudo.depositaddressgenerator.dto.DepositAddress;
import com.codewithudo.depositaddressgenerator.service.AddressService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
