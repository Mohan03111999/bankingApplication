package com.example.bankingApplication.exception;

public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(String s) {
        super(s);
    }
}
