package com.banksystem.banksystemappApp.controllers.usersControllers;

import com.banksystem.banksystemappApp.controllers.DTO.ThirdPartyDTO;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.services.TransactionService;
import com.banksystem.banksystemappApp.services.userService.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/third-party")
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @Autowired
    TransactionService  transactionService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAllAccountHolders(){
        return thirdPartyService.findAllThirdParty();
    }


    @PatchMapping("/payment/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account payment(@RequestBody ThirdPartyDTO thirdPartyDTO){
        return transactionService.thirdPartyPayment(thirdPartyDTO);
    }

    @PatchMapping("/receipts/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account receipts(@RequestBody ThirdPartyDTO thirdPartyDTO){
        return transactionService.thirdPartyReceipt(thirdPartyDTO);
    }

}
