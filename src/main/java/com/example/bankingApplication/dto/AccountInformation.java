package com.example.bankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInformation {
    private Long accountNumber;
    private String accountType;
    private Double currentBalance;
}
