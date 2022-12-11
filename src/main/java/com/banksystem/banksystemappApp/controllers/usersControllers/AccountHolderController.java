package com.banksystem.banksystemappApp.controllers.usersControllers;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.services.transactionService.TransactionService;
import com.banksystem.banksystemappApp.services.accountService.AccountService;
import com.banksystem.banksystemappApp.services.accountService.CreditCardService;
import com.banksystem.banksystemappApp.services.accountService.SavingsService;
import com.banksystem.banksystemappApp.services.userService.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    TransactionService  transactionService;

    @Autowired
    SavingsService  savingsService;

    @Autowired
    CreditCardService  creditCardService;


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAllAccountHolders(){
        return accountHolderService.findAllAccountHolders();
    }



    @GetMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getAccounBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        return accountService.showAccountBalance(userDetails , id);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction makeTransfer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.makeTransfer(userDetails , transactionDTO);
    }

    @GetMapping("/savings-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getSavingBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam String secretKey){
        return savingsService.showSavingBalance(userDetails, id , secretKey);
    }

    @PatchMapping("/deposit/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account deposit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam BigDecimal deposit){
        return transactionService.deposit(userDetails , id, deposit);
    }

    @PatchMapping("/withdrawal/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account withdrawal(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam BigDecimal withdrawal){
        return transactionService.withdrawal(userDetails , id, withdrawal);
    }

    @GetMapping("/credit-card/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCreditCardBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam Long secretKey){
        return creditCardService.showCreditCardBalance(userDetails ,id , secretKey);
    }



}
