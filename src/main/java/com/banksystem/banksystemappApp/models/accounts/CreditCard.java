package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class CreditCard extends Account {

        private BigDecimal creditLimit = new BigDecimal("100");

        private BigDecimal interestRate = new BigDecimal("0.20");


    public CreditCard() {
    }

    public CreditCard(BigDecimal balance, Long secretKey, AccountHolder primaryOwner,
                      AccountHolder secondaryOwner, BigDecimal penaltyFee, BigDecimal creditLimit,
                      BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCard(BigDecimal balance, Long secretKey, AccountHolder primaryOwner,
                      AccountHolder secondaryOwner, BigDecimal penaltyFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
    }



    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {

        BigDecimal limit = new BigDecimal("100000.00");

        if (creditLimit.compareTo(limit) > 0) {

            System.out.println("you have exceeded the maximum limit of 100.000");

            this.creditLimit = new BigDecimal("100000.00");

        }else{

            this.creditLimit = creditLimit;
        }

    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {

        BigDecimal interesRateLimit = new BigDecimal("0.10");

        if (interestRate.compareTo(interesRateLimit) < 0) {

            System.out.println("the interest rate may not be lower than 0.1");

            this.interestRate = new BigDecimal("0.10");

        }else{

            this.interestRate = interestRate;
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
