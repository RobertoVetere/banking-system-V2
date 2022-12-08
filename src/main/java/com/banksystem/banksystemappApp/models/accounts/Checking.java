package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountStatus;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

import static com.banksystem.banksystemappApp.enums.AccountType.CHECKING;

@Entity
public class Checking extends Account{

    @Nullable
    private BigDecimal minimumBalance = new BigDecimal("250.00");
    @Nullable
    private BigDecimal monthlyMaintenanceFee = new BigDecimal("12.00");




    public Checking() {

    }

    public Checking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                    BigDecimal penaltyFee,  BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, accountType);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;

    }
    public Checking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                    BigDecimal penaltyFee, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, accountType);
    }

    @Override
    public AccountType getAccountType() {
        return super.getAccountType();
    }

    @Override
    public void setAccountType(AccountType accountType) {
        super.setAccountType(AccountType.CHECKING);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public void setPrimaryOwner( AccountHolder primaryOwner) {
        super.setPrimaryOwner(primaryOwner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Checking checking = (Checking) o;
        return minimumBalance.equals(checking.minimumBalance) && monthlyMaintenanceFee.equals(checking.monthlyMaintenanceFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimumBalance, monthlyMaintenanceFee);
    }

    @Override
    public String toString() {
        return super.toString() + "Checking{" +
                "minimumBalance=" + minimumBalance +
                ", monthlyMaintenanceFee=" + monthlyMaintenanceFee +
                '}';
    }
}
