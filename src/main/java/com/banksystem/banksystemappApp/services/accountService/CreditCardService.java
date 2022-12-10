package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        Account creditCard = new CreditCard(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, null,
                accountDTO.getCreditLimit(),accountDTO.getInterestRate(), AccountType.CREDITCARD);

        String encodedPassword = passwordEncoder.encode(creditCard.getSecretKey());
        creditCard.setSecretKey(encodedPassword);
        creditCard = accountRepository.save(creditCard);

        return accountRepository.save(creditCard);
    }

    public BigDecimal showCreditCardBalance(Long id, Long secretKey) {
        CreditCard account = creditCardRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));


        LocalDate last = account.getCreatedDate();
        LocalDate now= LocalDate.now();
        Period period = Period.between ( last , now);


        int days = (period.getDays());

        if (secretKey.equals(account.getSecretKey())){
            if (days > 31){

                BigDecimal interest = account.getInterestRate().divide(new BigDecimal(100));
                account.setBalance(account.getBalance().multiply(interest));
                creditCardRepository.save(account);
            }
            return account.getBalance();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sorry, the password is incorrect");
    }
}
