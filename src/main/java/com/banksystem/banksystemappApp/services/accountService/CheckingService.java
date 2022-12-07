package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.accountsControllers.DTOs.AccountDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.StudentChecking;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.StudentCheckingRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CheckingService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public List<Checking> findAllCheckings() {
        return checkingRepository.findAll();
    }


    public Account addChecking(AccountDTO checkingDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;
        if(checkingDTO.getSecondaryOwnerId() != null) secondaryOwner = accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary owner not found"));

        if(Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24){
            Account studentChecking = new StudentChecking(checkingDTO.getBalance(), checkingDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal("20.0"));
            return accountRepository.save(studentChecking);

        }else{
            Account Checking = new Checking(checkingDTO.getBalance(), checkingDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal("20.0"));
            return accountRepository.save(Checking);
        }

    }
}
