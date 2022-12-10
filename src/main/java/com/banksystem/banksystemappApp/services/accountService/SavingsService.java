package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

    public BigDecimal showSavingBalance(Long id, String secretKey) {

        Savings account = savingsRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (secretKey.equals(account.getSecretKey())){

            LocalDate last = account.getCreatedDate();
            LocalDate now= LocalDate.now();
            Period period = Period.between ( last , now);

            /*
            LocalDate nextInterestPayment = last.withYear(now.getYear());

            if (nextInterestPayment.isBefore(last) || nextInterestPayment.isEqual(last)) {

                account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                savingsRepository.save(account);
            }

             */

            int days = (period.getDays());

            if (days > 365) {

                account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                savingsRepository.save(account);
            }
            return account.getBalance();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sorry, the password is incorrect");
        }

    }



