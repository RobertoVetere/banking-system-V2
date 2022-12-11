package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.users.User;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    public Account updateAccountBalance(Long id, BigDecimal balance) {

        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    public BigDecimal showAccountBalance(UserDetails userDetails , Long id) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        if (userDetails.getUsername().equals(accountRepository.findById(id).get().getPrimaryOwner().getUserName())){

                Account account = accountRepository.findById(id).orElseThrow
                        (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
                    return account.getBalance();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials");
    }
}
