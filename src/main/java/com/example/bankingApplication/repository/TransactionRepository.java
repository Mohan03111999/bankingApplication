package com.example.bankingApplication.repository;

import com.example.bankingApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<List<Transaction>> findByAccountNumber(Long accountNumber);
}
