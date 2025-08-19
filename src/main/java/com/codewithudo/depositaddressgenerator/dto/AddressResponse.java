package com.codewithudo.depositaddressgenerator.dto;

import lombok.Data;

@Data
public class AddressResponse {
    private String status;
    private DepositAddress data;
}