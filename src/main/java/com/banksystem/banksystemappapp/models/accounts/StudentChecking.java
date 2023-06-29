package com.banksystem.banksystemappapp.models.accounts;

import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class StudentChecking extends Account {

    public StudentChecking() {
    }

    public StudentChecking(BigDecimal balance, String secretKey, AccountHolder primaryOwner,
                           AccountHolder secondaryOwner, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, accountType);
    }

    @Override
    public void setAccountType(AccountType accountType) {
        super.setAccountType(AccountType.STUDENTCHECKING);
    }
}
