package com.banksystem.banksystemappApp.controllers.usersControllers;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.services.accountService.AccountService;
import com.banksystem.banksystemappApp.services.userService.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/account-holder")
public class AccountHolderController {

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    AccountService accountService;


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAllAccountHolders(){
        return accountHolderService.findAllAccountHolders();
    }

    @GetMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getAccounBalance(@PathVariable Long id, @RequestParam Long secretKey){
        return accountService.showAccountBalance(id , secretKey);
    }


}
