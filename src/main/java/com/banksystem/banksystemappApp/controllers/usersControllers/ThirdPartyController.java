package com.banksystem.banksystemappApp.controllers.usersControllers;

import com.banksystem.banksystemappApp.controllers.DTO.ThirdPartyDTO;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.services.TransactionService;
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
    TransactionService  transactionService;

    @GetMapping("/third-party-All")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAllAccountHolders(){
        return thirdPartyService.findAllThirdParty();
    }
/*
    @PostMapping("/third-party-transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction makeTransfer(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        return transactionService.thirdPartyDeposit(thirdPartyDTO);
    }

 */
}
