package com.banksystem.banksystemappApp.utilMethod;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class UtilMethod {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountRepository accountRepository;

    public UtilMethod() {
    }

    public Account calculateInterestRate(Account account){

        Savings saving = new Savings(account.getBalance(),account.getSecretKey(),account.getPrimaryOwner(),account.getSecondaryOwner(),account.getAccountType());

        LocalDate now = LocalDate.now();
        Period period = Period.between (account.getCreatedDate() , saving.getCheckLastConnection());


        if (period.getDays() > 365 & period.getDays() <= 730) {

            saving.setBalance(account.getBalance().multiply(BigDecimal.valueOf(saving.getInterestRate())));
            savingsRepository.save(saving);

        }else {

            Period period2 = Period.between (now , saving.getCheckLastConnection());

            if (period.getDays() > 365){

                saving.setBalance(saving.getBalance().multiply(BigDecimal.valueOf(saving.getInterestRate())));
                savingsRepository.save(saving);
            }
        }
        saving.setCheckLastConnection(now);
        return saving;
    }
}
