package com.banksystem.banksystemappApp.services.transactionService;

import com.banksystem.banksystemappApp.controllers.DTO.ThirdPartyTransactionDTO;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.enums.TransactionType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    UserRepository userRepository;


    public Transaction makeTransfer(UserDetails userDetails , TransactionDTO transactionDTO) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        Account transactionOwner = accountRepository.findByAccountNumber(transactionDTO.getTransactionOwnerAccountNumber());

        Account targetAccount = accountRepository.findByAccountNumber(transactionDTO.getTargetAccountNumber());

        if (userDetails.getUsername().equals(transactionOwner.getPrimaryOwner().getUserName()) ||
                userDetails.getUsername().equals(transactionOwner.getSecondaryOwner().getUserName())){

                    if (transactionDTO.getTransactionOwnerAccountNumber().equals(transactionOwner.getAccountNumber())){

                        if (transactionOwner.getBalance().compareTo(transactionDTO.getAmount()) > 0) {

                            transactionOwner.setBalance(transactionOwner.getBalance().subtract(transactionDTO.getAmount()));
                            targetAccount.setBalance(targetAccount.getBalance().add(transactionDTO.getAmount()));

                                accountRepository.saveAll(List.of(transactionOwner, targetAccount));

                                    Transaction transaction = new Transaction(transactionDTO.getTransactionOwnerAccountNumber(), transactionDTO.getTargetAccountNumber(),
                                            transactionOwner, targetAccount, transactionDTO.getTargetOwnerName(), transactionDTO.getAmount(),TransactionType.TRANSFER);

                    return transactionRepository.save(transaction);

                } else {

                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "sorry insufficient funds");
                }
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sorry credentials wrong");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "SORRY INCORRECT DATA");
    }

    public Account deposit(UserDetails userDetails , Long id, BigDecimal deposit) {

        userRepository.findByUserName(userDetails.getUsername()).get();



        Account account = accountRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

            if (userDetails.getUsername().equals(account.getPrimaryOwner().getUserName())){

                    account.setBalance(account.getBalance().add(deposit));

                        Transaction transaction = new Transaction(account.getAccountNumber(),account,deposit, TransactionType.DEPOSIT);
                        transactionRepository.save(transaction);

                    return accountRepository.save(account);

                    }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "SORRY INCORRECT DATA");
    }

    public Account withdrawal(UserDetails userDetails , Long id, BigDecimal withdrawal) {

        userRepository.findByUserName(userDetails.getUsername()).get();

            Account account = accountRepository.findById(id).orElseThrow
                        (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

                if (userDetails.getUsername().equals(accountRepository.findById(id).get().getPrimaryOwner().getUserName()) ){

                    if (account.getBalance().compareTo(withdrawal) < 0) {

                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry insufficient funds");

                        }else{

                            account.setBalance(account.getBalance().subtract(withdrawal));

                                Transaction transaction = new Transaction(account.getAccountNumber(),account,withdrawal, TransactionType.WITHDRAW);
                                transactionRepository.save(transaction);

            }
                return accountRepository.save(account);

        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "SORRY INCORRECT DATA");

    }

    public Account thirdPartyPayment(ThirdPartyTransactionDTO thirdPartyTransactionDTO,String secretKey, String hashedKey) {

        Account account = accountRepository.findByAccountNumber(thirdPartyTransactionDTO.getAccountNumber());

        //if (account.getSecretKey().equals(secretKey)){

            if (account.getBalance().compareTo(thirdPartyTransactionDTO.getAmount()) > 0){

                account.setBalance(account.getBalance().subtract(thirdPartyTransactionDTO.getAmount()));

            }else {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry insufficient funds");
            }
/*
        }else{

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "¡UPS! Password Wrong");

        }

 */
        Transaction transaction = new Transaction(account.getAccountNumber(),account, thirdPartyTransactionDTO.getAmount(), TransactionType.THIRD_PARTY_PAYMENT);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }


    public Account thirdPartyReceipt(ThirdPartyTransactionDTO thirdPartyTransactionDTO , String secretKey,String hashedKey) {

        Account account = accountRepository.findByAccountNumber(thirdPartyTransactionDTO.getAccountNumber());

                account.setBalance(account.getBalance().add(thirdPartyTransactionDTO.getAmount()));

        Transaction transaction = new Transaction(account.getAccountNumber(),account, thirdPartyTransactionDTO.getAmount(), TransactionType.THIRD_PARTY_RECEIPTS);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }
}
