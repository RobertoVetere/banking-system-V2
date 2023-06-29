package com.banksystem.banksystemappapp.controllers.usersControllers;

import com.banksystem.banksystemappapp.dtos.AccountDTO;
import com.banksystem.banksystemappapp.dtos.AccountHolderDTO;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.accounts.Checking;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.ThirdParty;
import com.banksystem.banksystemappapp.services.accountService.AccountService;
import com.banksystem.banksystemappapp.services.accountService.CheckingService;
import com.banksystem.banksystemappapp.services.accountService.CreditCardService;
import com.banksystem.banksystemappapp.services.accountService.SavingsService;
import com.banksystem.banksystemappapp.services.userService.AccountHolderService;
import com.banksystem.banksystemappapp.services.userService.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {


    @Autowired
    SavingsService savingsService;

    @Autowired
    CheckingService checkingService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    AccountService accountService;

    @Autowired
    ThirdPartyService thirdPartyService;

    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/checking-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAllCheckings() {
        return checkingService.findAllCheckings();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create-account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addNewThirdParty(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AccountHolderDTO accountHolderDTO) {
        return accountHolderService.addAccountHolder(userDetails, accountHolderDTO);
    }

    @PostMapping("/add-checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewChecking(@RequestBody AccountDTO checking) {
        return checkingService.addChecking(checking);
    }

    @PostMapping("/add-saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewSaving(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AccountDTO saving) {
        return savingsService.addSaving(userDetails, saving);
    }

    @PostMapping("/add-credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewCreditCard(@RequestBody AccountDTO creditCard) {
        return creditCardService.addCreditCard(creditCard);
    }


    @PatchMapping("/update-balance/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateAccountBalance(@PathVariable Long id, @RequestParam BigDecimal balance) {
        return accountService.updateAccountBalance(id, balance);
    }


    @PostMapping("/add-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addNewThirdParty(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ThirdParty thirdParty) {
        return thirdPartyService.addThirdParty(userDetails, thirdParty);
    }

    @DeleteMapping("/delete-checking/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id) {
        checkingService.deleteChecking(id);
    }


}
