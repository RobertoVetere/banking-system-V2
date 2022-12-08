package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.accountsControllers.DTOs.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<CreditCard> findAllCreditCard() {
        return creditCardRepository.findAll();
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public Account addCreditCard(AccountDTO accountDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;

        Account creditCard = new CreditCard(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner,
                new BigDecimal("40.00"),accountDTO.getCreditLimit(),accountDTO.getInterestRate(), AccountType.CREDITCARD);
        return accountRepository.save(creditCard);
    }
}
