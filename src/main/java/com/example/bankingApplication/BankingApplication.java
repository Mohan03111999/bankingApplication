package com.example.bankingApplication;

import com.example.bankingApplication.entity.Account;
import com.example.bankingApplication.entity.Customer;
import com.example.bankingApplication.repository.AccountRepository;
import com.example.bankingApplication.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BankingApplication {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@PostConstruct
	public void init(){
		Customer customer = new Customer(1l,"Mohan",12345l,"Chennai","mohan@gmail.com",
				"9840384699","FBNPM2123B");
		customerRepository.save(customer);

		Account account = new Account(1l,123456789l,"SAVINGS",42500.00);
		Account account2 = new Account(2l,987654321l,"SAVINGS",12500.00);
		accountRepository.save(account);
		accountRepository.save(account2);
		}
	}

