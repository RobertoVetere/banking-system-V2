package com.banksystem.banksystemappApp.services.userService;

import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public List<AccountHolder> findAllAccountHolders() {
        return accountHolderRepository.findAll();
    }
}
