package com.banksystem.banksystemappapp.controllers.bankContollers;
import com.banksystem.banksystemappapp.models.bank.Bank;
import com.banksystem.banksystemappapp.models.users.Admin;
import com.banksystem.banksystemappapp.services.bankService.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank")
public class BankController {

    @Autowired
    BankService bankService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create-bank")
    @ResponseStatus(HttpStatus.CREATED)
    public Bank addNewBank(@RequestBody Bank bank) {
        return bankService.createBank(bank);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addNewAdmin(@RequestBody Admin admin) {
        return bankService.createAdmin(admin);
    }
}
