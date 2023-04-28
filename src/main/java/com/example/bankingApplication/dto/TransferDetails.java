package com.example.bankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetails {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private Double transferredAmount;

}
