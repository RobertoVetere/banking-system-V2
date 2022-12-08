package com.banksystem.banksystemappApp.services;

import com.banksystem.banksystemappApp.controllers.transactionDTO.TransactionDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.repositories.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    /*
    public Transaction makeTransfer(Long id, Account transactionOwner, Account targetAccount, BigDecimal amount) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found"));

        if (transactionOwner.getBalance().compareTo(amount) < 0) {
            System.out.println("Sorry, insufficient founds");
        }else{
            transactionOwner.setBalance(transactionOwner.getBalance().subtract(amount));
            targetAccount.setBalance(targetAccount.getBalance().add(amount));
            System.out.println("Transaction is correctly");
            accountRepository.saveAll(List.of(transactionOwner,targetAccount));
        }
        return transactionRepository.save(transaction);
    }

     */

    public Transaction makeTransfer(TransactionDTO transactionDTO) {

        Account transactionOwner = accountRepository.findByAccountNumber(transactionDTO.getTransactionOwnerAccountNumber());

        Account targetAccount = accountRepository.findByAccountNumber(transactionDTO.getTargetAccountNumber());

        if (transactionOwner.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            System.out.println("Sorry, insufficient founds");
        }else{
            transactionOwner.setBalance(transactionOwner.getBalance().subtract(transactionDTO.getAmount()));
            targetAccount.setBalance(targetAccount.getBalance().add(transactionDTO.getAmount()));
            System.out.println("Transaction is correctly");
            accountRepository.saveAll(List.of(transactionOwner,targetAccount));
        }
        Transaction transaction = new Transaction(transactionDTO.getTransactionOwnerAccountNumber(),transactionDTO.getTargetAccountNumber(),
                transactionOwner, targetAccount,transactionDTO.getTargetOwnerName(),transactionDTO.getAmount());

        return transactionRepository.save(transaction);
    }
}
