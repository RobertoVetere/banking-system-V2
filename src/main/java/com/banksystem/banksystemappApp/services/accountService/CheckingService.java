package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.StudentChecking;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.StudentCheckingRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public List<Checking> findAllCheckings() {
        return checkingRepository.findAll();
    }


    public Account addChecking(UserDetails userDetails , AccountDTO accountDTO) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;
        if(accountDTO.getSecondaryOwnerId() != null) secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary owner not found"));

        if(Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24){
            Account studentChecking = new StudentChecking(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, AccountType.STUDENTCHECKING);

            String encodedPassword = passwordEncoder.encode(studentChecking.getSecretKey());
            studentChecking.setSecretKey(encodedPassword);
            studentChecking = accountRepository.save(studentChecking);

            return accountRepository.save(studentChecking);

        }else{
            Account Checking = new Checking(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, AccountType.CHECKING);

            String encodedPassword = passwordEncoder.encode(Checking.getSecretKey());
            Checking.setSecretKey(encodedPassword);
            Checking = accountRepository.save(Checking);

            return accountRepository.save(Checking);
        }
    }

    public void deleteChecking(UserDetails userDetails , Long id){

        userRepository.findByUserName(userDetails.getUsername()).get();

        Account account = accountRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.deleteById(id);
    }
}
