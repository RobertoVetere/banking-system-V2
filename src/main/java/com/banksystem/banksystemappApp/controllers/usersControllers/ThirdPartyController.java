package com.banksystem.banksystemappApp.controllers.usersControllers;

import com.banksystem.banksystemappApp.controllers.DTO.ThirdPartyTransactionDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.services.transactionService.TransactionService;
import com.banksystem.banksystemappApp.services.userService.ThirdPartyService;
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
