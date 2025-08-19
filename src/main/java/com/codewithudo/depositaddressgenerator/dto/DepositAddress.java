package com.codewithudo.depositaddressgenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepositAddress {
    private String id;
    private String address;

    @JsonProperty("created_at")
    private String createdAt;
}