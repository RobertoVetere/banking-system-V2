package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class StudentChecking extends Account {

    public StudentChecking() {
    }

    public StudentChecking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner,
                           AccountHolder secondaryOwner,AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner,accountType);
    }

    @Override
    public AccountType getAccountType() {
        return super.getAccountType();
    }

    @Override
    public void setAccountType(AccountType accountType) {
        super.setAccountType(AccountType.STUDENTCHECKING);
    }
}
