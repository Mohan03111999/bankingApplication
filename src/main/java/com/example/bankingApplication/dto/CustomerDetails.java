package com.example.bankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetails {
    private String name;
    private Long customerNumber;
    private String address;
    private String email;
    private String phoneNumber;
    private String PAN;
}
