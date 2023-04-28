package com.example.bankingApplication.Controller;

import com.example.bankingApplication.dto.AccountInformation;
import com.example.bankingApplication.dto.TransactionDetails;
import com.example.bankingApplication.dto.TransferDetails;
import com.example.bankingApplication.service.IBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private IBankingService bankingService;

    @PostMapping("/create")
    public String createAccount(@RequestBody AccountInformation account, @RequestParam Long customerNumber){
        return bankingService.addAccount(account,customerNumber);
    }

    @GetMapping("/account/{accountNumber}")
    public AccountInformation findByAccountId(@PathVariable Long accountNumber){
        return bankingService.findByAccountNumber(accountNumber);
    }
    @GetMapping("/account/transactions/{accountNumber}")
    public List<TransactionDetails> findAllTransactions(@PathVariable Long accountNumber){
        return bankingService.findByTransactionByAccountNumber(accountNumber);
    }

    @PutMapping("/transfer/{customerNumber}")
    public String transferAmount(@RequestBody TransferDetails transferDetails, @PathVariable Long customerNumber){
        return bankingService.transferAmount(transferDetails,customerNumber);
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public Double withdrawAmount(@PathVariable Long accountNumber, @PathVariable Double amount){
        return bankingService.withdrawMoney(accountNumber,amount);
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Double depositMoney(@PathVariable Long accountNumber, @PathVariable Double amount){
        return bankingService.depositMoney(accountNumber,amount);
    }

}
