package com.banksystem.banksystemappApp.services.transactionService;

import com.banksystem.banksystemappApp.controllers.DTO.ThirdPartyDTO;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.enums.TransactionType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;



    public Transaction makeTransfer(TransactionDTO transactionDTO) {

        Account transactionOwner = accountRepository.findByAccountNumber(transactionDTO.getTransactionOwnerAccountNumber());

        Account targetAccount = accountRepository.findByAccountNumber(transactionDTO.getTargetAccountNumber());

        if (targetAccount.getPrimaryOwner().getName().equals(transactionDTO.getTargetOwnerName())
                & targetAccount.getAccountNumber().equals(transactionDTO.getTargetAccountNumber())
                ||
                targetAccount.getSecondaryOwner().getName().equals(transactionDTO.getTargetOwnerName())
                        & targetAccount.getAccountNumber().equals(transactionDTO.getTargetAccountNumber())) {

                if (transactionOwner.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sorry insufficient funds");
                } else {
                    transactionOwner.setBalance(transactionOwner.getBalance().subtract(transactionDTO.getAmount()));
                    targetAccount.setBalance(targetAccount.getBalance().add(transactionDTO.getAmount()));


                    accountRepository.saveAll(List.of(transactionOwner, targetAccount));

                    Transaction transaction = new Transaction(transactionDTO.getTransactionOwnerAccountNumber(), transactionDTO.getTargetAccountNumber(),
                            transactionOwner, targetAccount, transactionDTO.getTargetOwnerName(), transactionDTO.getAmount(),TransactionType.TRANSFER);
                    return transactionRepository.save(transaction);
                }
            }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the account number is invalid");
    }

    public Account deposit(Long id, BigDecimal deposit) {
        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        account.setBalance(account.getBalance().add(deposit));

            Transaction transaction = new Transaction(account.getAccountNumber(),account,deposit, TransactionType.DEPOSIT);
            transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    public Account withdrawal(Long id, BigDecimal withdrawal) {
        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getBalance().compareTo(withdrawal) < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry insufficient funds");
        }else{

            account.setBalance(account.getBalance().subtract(withdrawal));

                Transaction transaction = new Transaction(account.getAccountNumber(),account,withdrawal, TransactionType.WITHDRAW);
                transactionRepository.save(transaction);

        }

        return accountRepository.save(account);
    }

    public Account thirdPartyPayment(ThirdPartyDTO thirdPartyDTO) {

        Account account = accountRepository.findByAccountNumber(thirdPartyDTO.getAccountNumber());


        if (thirdPartyDTO.getSecretKey().equals(account.getSecretKey())){

            if (account.getBalance().compareTo(thirdPartyDTO.getAmount()) > 0){

                account.setBalance(account.getBalance().subtract(thirdPartyDTO.getAmount()));

            }else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry insufficient funds");
            }

        }else{

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "¡UPS! Password Wrong");

        }


        Transaction transaction = new Transaction(account.getAccountNumber(),account,thirdPartyDTO.getAmount(), TransactionType.THIRD_PARTY_PAYMENT);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }


    public Account thirdPartyReceipt(ThirdPartyDTO thirdPartyDTO) {

        Account account = accountRepository.findByAccountNumber(thirdPartyDTO.getAccountNumber());

                account.setBalance(account.getBalance().add(thirdPartyDTO.getAmount()));

        Transaction transaction = new Transaction(account.getAccountNumber(),account,thirdPartyDTO.getAmount(), TransactionType.THIRD_PARTY_RECEIPTS);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }
}
