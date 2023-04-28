package com.example.bankingApplication.Util;

import com.example.bankingApplication.dto.AccountInformation;
import com.example.bankingApplication.dto.CustomerDetails;
import com.example.bankingApplication.dto.TransactionDetails;
import com.example.bankingApplication.dto.TransferDetails;
import com.example.bankingApplication.entity.Account;
import com.example.bankingApplication.entity.Customer;
import com.example.bankingApplication.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Utility {
    public CustomerDetails convertToCustomerDTO(Customer customer){
        return CustomerDetails.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .customerNumber(customer.getCustomerNumber())
                .PAN(customer.getPAN())
                .build();
    }
    public Customer convertToCustomerEntity(CustomerDetails customerDetails){
        return Customer.builder()
                .name(customerDetails.getName())
                .email(customerDetails.getEmail())
                .phoneNumber(customerDetails.getPhoneNumber())
                .address(customerDetails.getAddress())
                .customerNumber(customerDetails.getCustomerNumber())
                .PAN(customerDetails.getPAN())
                .build();
    }

    public AccountInformation convertToAccountDTO(Account account) {
        return AccountInformation.builder()
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .currentBalance(account.getCurrentBalance())
                .build();
    }

    public Account convertToAccountEntity(AccountInformation accountInformation) {
        return Account.builder()
                .accountType(accountInformation.getAccountType())
                .accountNumber(accountInformation.getAccountNumber())
                .currentBalance(accountInformation.getCurrentBalance())
                .build();
    }

    public TransactionDetails convertToTransactionDTO(Transaction transaction) {
        return TransactionDetails.builder()
                .accountNumber(transaction.getAccountNumber())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionDTime(transaction.getTransactionDTime())
                .transactionType(transaction.getTransactionType())
                .build();
    }
    public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {
        return Transaction.builder()
                .accountNumber(transactionDetails.getAccountNumber())
                .transactionAmount(transactionDetails.getTransactionAmount())
                .transactionDTime(transactionDetails.getTransactionDTime())
                .transactionType(transactionDetails.getTransactionType())
                .build();
    }

    public Transaction createTransaction(TransferDetails transferDetails, Long accountNumber, String transactionType) {
        return Transaction.builder()
                .accountNumber(accountNumber)
                .transactionType(transactionType)
                .transactionAmount(transferDetails.getTransferredAmount())
                .transactionDTime(LocalDateTime.now())
                .build();
    }
}
