package com.banksystem.banksystemappApp.services.bankService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountHolderDTO;
import com.banksystem.banksystemappApp.models.bank.Bank;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Role;
import com.banksystem.banksystemappApp.repositories.bankRepository.BankRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;
    @Autowired
    PasswordEncoder passwordEncoder;



    public Bank createBank(Bank bank) {

        Bank newBank = new Bank(bank.getName(), bank.getPassword());

        String encodedPassword = passwordEncoder.encode(newBank.getPassword());
        newBank.setPassword(encodedPassword);

        return bankRepository.save(newBank);
    }

}
