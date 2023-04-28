package com.example.bankingApplication.service.impl;

import com.example.bankingApplication.Util.Utility;
import com.example.bankingApplication.dto.AccountInformation;
import com.example.bankingApplication.dto.CustomerDetails;
import com.example.bankingApplication.dto.TransactionDetails;
import com.example.bankingApplication.dto.TransferDetails;
import com.example.bankingApplication.entity.Account;
import com.example.bankingApplication.entity.Customer;
import com.example.bankingApplication.entity.Transaction;
import com.example.bankingApplication.exception.AccountNotFound;
import com.example.bankingApplication.exception.CustomerNotFound;
import com.example.bankingApplication.exception.InsufficientBalance;
import com.example.bankingApplication.repository.AccountRepository;
import com.example.bankingApplication.repository.CustomerRepository;
import com.example.bankingApplication.repository.TransactionRepository;
import com.example.bankingApplication.service.IBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankingServiceImpl implements IBankingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private Utility utility;

    @Override
    public List<CustomerDetails> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(c ->utility.convertToCustomerDTO(c)).collect(Collectors.toList());
    }

    @Override
    public String addCustomer(CustomerDetails customerDetails) {
        String message;
        Customer customer= utility.convertToCustomerEntity(customerDetails);
        if(customerRepository.findByCustomerNumber(customer.getCustomerNumber()).isPresent()){
            message = "Customer Already exists";
        }else {
            customerRepository.save(customer);
            message = "Added Customer Successfully";
        }
        return message;
    }

    @Override
    public CustomerDetails findByCustomerNumber(Long customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber).
                orElseThrow(()->new CustomerNotFound("Customer not found"));
        return utility.convertToCustomerDTO(customer);
    }

    @Override
    public AccountInformation findByAccountNumber(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).
                orElseThrow(()->new AccountNotFound("Account does not exist"));
        return utility.convertToAccountDTO(account);
    }

    @Override
    public String addAccount(AccountInformation accountInformation, Long customerNumber) {
        String message;
        if(findByCustomerNumber(customerNumber) != null){
            accountRepository.save(utility.convertToAccountEntity(accountInformation));
            message = "Added Account Successfully";
        }else {
            message = "Customer does not exist";
        }
        return message;
    }

    @Override
    public String transferAmount(TransferDetails transferDetails, Long customerNumber) {
        List<Account> accountEntities = new ArrayList<>();
        Account fromAccount = null;
        Account toAccount = null;

        Optional<Customer> customerEntity = customerRepository.findByCustomerNumber(customerNumber);

        if(customerEntity.isPresent()) {

            Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
            if(fromAccountEntityOpt.isPresent()) {
                fromAccount = fromAccountEntityOpt.get();
            }
            else {
                throw new AccountNotFound(transferDetails.getFromAccountNumber() + " not found.");
            }

            Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
            if(toAccountEntityOpt.isPresent()) {
                toAccount = toAccountEntityOpt.get();
            }
            else {
               throw new AccountNotFound(transferDetails.getToAccountNumber() + " not found.");
            }

            if(fromAccount.getCurrentBalance() < transferDetails.getTransferredAmount()) {
                throw new InsufficientBalance("Insufficient Funds.");
            }
            else {
                if((fromAccount.getCurrentBalance() - transferDetails.getTransferredAmount()) < fromAccount.getMINIMUM_BALANCE()) {
                    throw new InsufficientBalance("Alert: Amount cannot be transferred, less than minimum balance");
                }else {
                    fromAccount.setCurrentBalance(fromAccount.getCurrentBalance() - transferDetails.getTransferredAmount());
                    accountEntities.add(fromAccount);

                    toAccount.setCurrentBalance(toAccount.getCurrentBalance() + transferDetails.getTransferredAmount());
                    accountEntities.add(toAccount);

                    accountRepository.saveAll(accountEntities);

                    // Create transaction for FROM Account
                    Transaction fromTransaction = utility.createTransaction(transferDetails, fromAccount.getAccountNumber(), "DEBIT");
                    transactionRepository.save(fromTransaction);

                    // Create transaction for TO Account
                    Transaction toTransaction = utility.createTransaction(transferDetails, toAccount.getAccountNumber(), "CREDIT");
                    transactionRepository.save(toTransaction);
                    return "Amount transferred for Customer Number " + customerNumber;
                }
            }
        } else {
            return "Customer Number " + customerNumber + " not found.";
        }
    }

    @Override
    public List<TransactionDetails> findByTransactionByAccountNumber(Long accountNumber) {
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        if(findByAccountNumber(accountNumber) != null){
            Optional<List<Transaction>> transactions = transactionRepository.findByAccountNumber(accountNumber);
            if(transactions.isPresent()){
                transactions.get().forEach(t->transactionDetails.add(utility.convertToTransactionDTO(t)));
            }
        }
        return transactionDetails;
    }

    @Override
    public Double withdrawMoney(Long accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElse(null);
        if (account != null) {
            if (account.getCurrentBalance() >= amount && (account.getCurrentBalance() - amount)>account.getMINIMUM_BALANCE()) {
                account.setCurrentBalance((account.getCurrentBalance() - amount));
                Transaction transaction = new Transaction();
                transaction.setTransactionAmount(amount);
                transaction.setTransactionDTime(LocalDateTime.now());
                transaction.setTransactionType("Withdraw");
                transactionRepository.save(transaction);
                accountRepository.save(account);
                return account.getCurrentBalance();
            } else {
                throw new InsufficientBalance("Insufficient balance");
            }
        } else {
            throw new AccountNotFound("Account not found");
        }

    }

    @Override
    public Double depositMoney(Long accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElse(null);
        if (account != null) {
                account.setCurrentBalance((account.getCurrentBalance() + amount));
                Transaction transaction = new Transaction();
                transaction.setTransactionAmount(amount);
                transaction.setTransactionDTime(LocalDateTime.now());
                transaction.setTransactionType("Deposit");
                transactionRepository.save(transaction);
                accountRepository.save(account);
                return account.getCurrentBalance();
            } else {
                throw new AccountNotFound("Account Not Found");
            }
        }

}
