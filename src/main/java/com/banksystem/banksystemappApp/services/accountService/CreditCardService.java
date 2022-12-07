package com.banksystem.banksystemappApp.services.accountService;

import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    public List<CreditCard> findAllCreditCard() {
        return creditCardRepository.findAll();
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }
}
