package com.banksystem.banksystemappapp.controllers.usersControllers;

import com.banksystem.banksystemappapp.dtos.ThirdPartyTransactionDTO;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.users.ThirdParty;
import com.banksystem.banksystemappapp.services.transactionService.TransactionService;
import com.banksystem.banksystemappapp.services.userService.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/third-party")
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAllAccountHolders() {
        return thirdPartyService.findAllThirdParty();
    }


    @PatchMapping("/payment/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account payment(@RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO, @RequestParam String secretKey, String hashedKey) {
        return transactionService.thirdPartyPayment(thirdPartyTransactionDTO, secretKey, hashedKey);
    }

    @PatchMapping("/receipts/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account receipts(@RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO, @RequestParam String secretKey, String hashedKey) {
        return transactionService.thirdPartyReceipt(thirdPartyTransactionDTO, secretKey, hashedKey);
    }

}
