package com.banksystem.banksystemappApp.controllers.accountsControllers;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.services.accountService.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/credit-card-All")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAllCreditCard(){
        return creditCardService.findAllCreditCard();
    }

    @PostMapping("/create-credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.saveCreditCard(creditCard);
    }
}
