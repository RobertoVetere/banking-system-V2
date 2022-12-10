package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Account updateAccountBalance(Long id, BigDecimal balance) {
        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    public BigDecimal showAccountBalance(Long id, String secretKey) {

        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getSecretKey().equals(secretKey)){

            return account.getBalance();

        }else{

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sorry, the password is incorrect");
        }
    }

}
