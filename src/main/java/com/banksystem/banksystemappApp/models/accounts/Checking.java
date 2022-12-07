package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Checking extends Account{

    @Nullable
    private BigDecimal minimumBalance = new BigDecimal("250.00");
    @Nullable
    private BigDecimal monthlyMaintenanceFee = new BigDecimal("12.00");;


    public Checking() {

    }

    public Checking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                    BigDecimal penaltyFee, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }
    public Checking(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                    BigDecimal penaltyFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
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

    /*
    public static void accountHolderAgeValidate(AccountHolder primaryOwner, Checking checking, StudentChecking studentChecking){
        LocalDate dB= primaryOwner.getDateOfBirth();
        LocalDate now = LocalDate.now();

        if (dB.isBefore(now.minusYears(24))){
            System.out.println("es mayor de 24 años");
        }else{
            System.out.println("es menor de 24 años");
        }

    }

     */
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
