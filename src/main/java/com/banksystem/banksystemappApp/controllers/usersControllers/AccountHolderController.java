package com.banksystem.banksystemappApp.controllers.usersControllers;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.services.userService.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountHolderController {

    @Autowired
    AccountHolderService accountHolderService;


    @GetMapping("/account-holders-All")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAllAccountHolders(){
        return accountHolderService.findAllAccountHolders();
    }
}
