package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.User;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import com.banksystem.banksystemappApp.utilMethod.UtilMethod;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    UtilMethod utilMethod;


    public Account addSaving(UserDetails userDetails , AccountDTO accountDTO) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;
        if(accountDTO.getSecondaryOwnerId() != null) secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary owner not found"));

            Account saving = new Savings(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner,AccountType.SAVINGS);

            String encodedPassword = passwordEncoder.encode(saving.getSecretKey());
            saving.setSecretKey(encodedPassword);
            saving = accountRepository.save(saving);

            return accountRepository.save(saving);
    }

    public BigDecimal showSavingBalance(UserDetails userDetails , Long id, String secretKey) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        User holder = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        Savings account = savingsRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

            //utilMethod.calculateInterestRate(account);

        if (holder.getUserName().equals(account.getPrimaryOwner().getUserName())){

            if (account.getSecretKey().equals(secretKey)){

                LocalDate now = LocalDate.now();
                Period period = Period.between (account.getCreatedDate() , account.getCheckLastConnection());


                if (period.getDays() > 365 & period.getDays() <= 730) {

                    account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                    savingsRepository.save(account);

                }else {

                    Period period2 = Period.between (now , account.getCheckLastConnection());

                    if (period.getDays() > 365){

                        account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                        savingsRepository.save(account);
                    }
                }

                account.setCheckLastConnection(now);
            }

            return account.getBalance();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sorry, the password is incorrect");

        }

    }



