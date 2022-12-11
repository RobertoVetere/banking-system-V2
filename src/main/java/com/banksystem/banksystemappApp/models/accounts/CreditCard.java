package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class CreditCard extends Account {
        @Nullable
        private BigDecimal creditLimit = new BigDecimal("100");
        @Nullable
        private BigDecimal interestRate = new BigDecimal("0.20");

        private LocalDate checkLastConnection;


    public CreditCard() {
    }

    public CreditCard(BigDecimal balance, String secretKey, AccountHolder primaryOwner,
                      AccountHolder secondaryOwner, BigDecimal creditLimit,
                      BigDecimal interestRate, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner,  accountType);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public LocalDate getCheckLastConnection() {
        return checkLastConnection;
    }

    public void setCheckLastConnection(LocalDate checkLastConnection) {
        this.checkLastConnection = checkLastConnection;
    }

    @Override
    public AccountType getAccountType() {
        return super.getAccountType();
    }

    @Override
    public void setAccountType(AccountType accountType) {
        super.setAccountType(AccountType.CREDITCARD);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {

        try{

            BigDecimal limit = new BigDecimal("100000.00");

                if (creditLimit.compareTo(limit) > 0) {

                    this.creditLimit = limit;

                }else{

                    this.creditLimit = creditLimit;
                }

        }catch(IllegalArgumentException exception){

            throw new IllegalArgumentException("Please, set credit limit under 100.000");
        }


    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {

      try{

        BigDecimal interesRateLimit = new BigDecimal("0.10");

        if (interestRate.compareTo(interesRateLimit) < 0) {

            this.interestRate = interesRateLimit;

        }else{

            this.interestRate = interestRate;
        }

      }catch(IllegalArgumentException exception){

          throw new IllegalArgumentException("Please, set credit limit over 0.10");

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditCard that = (CreditCard) o;
        return creditLimit.equals(that.creditLimit) && interestRate.equals(that.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creditLimit, interestRate);
    }

    @Override
    public String toString() {
        return super.toString() + "CreditCard{" +
                "creditLimit=" + creditLimit +
                ", interestRate=" + interestRate +
                '}';
    }
}
