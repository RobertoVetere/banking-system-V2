package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.accountsControllers.DTOs.AccountDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.StudentCheckingRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Account updateAccountBalance(Long id, BigDecimal balance) {
        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setBalance(balance);
        return accountRepository.save(account);
    }

}
