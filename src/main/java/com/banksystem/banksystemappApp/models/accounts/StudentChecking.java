package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class StudentChecking extends Account {

    public StudentChecking() {
    }

    public StudentChecking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner,
                           AccountHolder secondaryOwner, BigDecimal penaltyFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
    }
}
