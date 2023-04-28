package com.example.bankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {
    private Long accountNumber;
    private Double transactionAmount;
    private String transactionType;

    private LocalDateTime transactionDTime;
}
