package com.example.bankingApplication.service;


import com.example.bankingApplication.dto.AccountInformation;
import com.example.bankingApplication.dto.CustomerDetails;
import com.example.bankingApplication.dto.TransactionDetails;
import com.example.bankingApplication.dto.TransferDetails;

import java.util.List;

public interface IBankingService {

    public List<CustomerDetails> findAll();
    public String addCustomer(CustomerDetails customerDetails);
    public CustomerDetails findByCustomerNumber(Long customerNumber);
    public AccountInformation findByAccountNumber(Long accountNumber);
    public String addAccount(AccountInformation accountInformation, Long customerNumber );
    public String transferAmount(TransferDetails transferDetails, Long customerNumber);
    public List<TransactionDetails> findByTransactionByAccountNumber(Long accountNumber);
    public Double withdrawMoney(Long accountNumber, Double amount);
    public Double depositMoney(Long accountNumber, Double amount);

}
