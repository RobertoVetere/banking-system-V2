package com.banksystem.banksystemappApp.controllers.accountsControllers;
import com.banksystem.banksystemappApp.controllers.accountsControllers.DTOs.AccountDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.services.accountService.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CheckingController {

    @Autowired
    CheckingService checkingService;

    @GetMapping("/checkings-All")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAllCheckings(){
        return checkingService.findAllCheckings();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewChecking(@RequestBody AccountDTO checking) {
        return checkingService.addChecking(checking);
    }
}
