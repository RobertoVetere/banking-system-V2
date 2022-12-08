package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.accountsControllers.DTOs.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.accounts.StudentChecking;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;


    public Account addSaving(AccountDTO accountDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;

            Account saving = new Savings(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner,AccountType.SAVINGS);
            return accountRepository.save(saving);
    }

    public BigDecimal showSavingBalance(Long id, Long secretKey) {


        Savings account = savingsRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (secretKey.equals(account.getSecretKey())){

           // LocalDate date = LocalDate.();

            if (Period.between(account.getCreatedDate(), LocalDate.now()).getYears() > 1) {
                //||Period.between(date, LocalDate.now()).getYears() < 1
                account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));

                //Savings Savings = new Savings(account.getBalance(),account.getSecretKey(),account.getPrimaryOwner(),
                  //      account.getSecondaryOwner(),account.getPenaltyFee(),AccountType.SAVINGS);

                //BigDecimal interesProfit = account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate()));
            }
            return account.getBalance();
        }
            return null;
        }

        public void deleteSaving(Long id){savingsRepository.deleteById(id);}
    }


/*
    public BigDecimal showSavingBalance(Long id, Long secretKey) {
        Savings account = savingsRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (secretKey.equals(account.getSecretKey())){
            return account.getBalance();
        }
        return null;
    }

 */

