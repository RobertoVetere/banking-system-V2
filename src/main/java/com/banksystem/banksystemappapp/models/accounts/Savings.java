package com.banksystem.banksystemappapp.models.accounts;

import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Savings extends Account {

    private BigDecimal minimumBalance = new BigDecimal("1000.00");
    private Double interestRate = 0.0025;

    private LocalDate checkLastConnection;

    public Savings() {
    }


    public Savings(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   BigDecimal minimumBalance, Double interestRate, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, accountType);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public Savings(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, accountType);
    }

    public LocalDate getCheckLastConnection() {
        return checkLastConnection;
    }

    public void setCheckLastConnection(LocalDate checkLastConnection) {
        this.checkLastConnection = checkLastConnection;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {

        try {

            BigDecimal limit = new BigDecimal("100.00");

            if (minimumBalance.compareTo(limit) < 0) {

                this.minimumBalance = limit;

            } else {

                this.minimumBalance = minimumBalance;

            }
        } catch (IllegalArgumentException exception) {

            throw new IllegalArgumentException("Please, set minimum balance ver 100");
        }
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {

        try {

            if (interestRate > 0.5) {

                this.interestRate = 0.5;

            } else {
                this.interestRate = interestRate;
            }
        } catch (IllegalArgumentException exception) {

            throw new IllegalArgumentException("Please, enter a correct interest rate");

        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Savings savings = (Savings) o;
        return minimumBalance.equals(savings.minimumBalance) && interestRate.equals(savings.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimumBalance, interestRate);
    }

    @Override
    public String toString() {
        return super.toString() + "Savings{" +
                "minimumBalance=" + minimumBalance +
                ", interestRate=" + interestRate +
                '}';
    }
}
